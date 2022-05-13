import React, {useEffect, useState} from "react";
import Autocomplete from "@mui/material/Autocomplete";
import TextField from "@mui/material/TextField";
import {BACKEND_URL} from "../Properties";

export const Location = (props: {
    init: {
        country: { value: string; label: string };
        city: { label: string; value: number };
    };
    setter: (id: number) => void;
}) => {
    const [countries, setCountries] = useState<{ value: string; label: string }[] | null>(null);
    const [countrySelected, setCountrySelected] = useState<{
        value: string;
        label: string;
    } | null>(props.init.country);
    const [cities, setCities] = useState<{ value: number; label: string }[] | null>(null);
    const [citySelected, setCitySelected] = useState<{
        value: number;
        label: string;
    } | null>(props.init.city);

    useEffect(() => {
        getCountries();
    }, []);

    useEffect(() => {
        getCities();
    }, [countrySelected]);

    useEffect(() => {
        if (citySelected === null) {
            return;
        }
        props.setter(citySelected.value)
    }, [citySelected])

    const getCountries = () => {
        fetch(`${BACKEND_URL}/location/en`)
            .then((response) => {
                return response.json();
            })
            .then((json) => {
                const result = json.map((country: any) => {
                    return {
                        value: country.value,
                        label: country.label,
                    };
                });
                setCountries(result);
            });
    };

    const getCities = () => {
        if (countrySelected === null) {
            return;
        }
        fetch(`${BACKEND_URL}/location/en/${countrySelected.value}`)
            .then((response) => response.json())
            .then((json) => {
                const result = json.map((city: any) => {
                    return {
                        value: city.value,
                        label: city.label,
                    };
                });
                setCities(result);
            });
    };

    return (
        <>
            <div>Location</div>
            {countries && (
                <Autocomplete
                    value={countrySelected}
                    options={countries}
                    renderInput={(params) => <TextField {...params} />}
                    isOptionEqualToValue={(option, value) => option.value === value.value}
                    sx={{width: 400}}
                    onChange={(
                        event: any,
                        newCountrySelect: { value: string; label: string } | null
                    ) => {
                        setCountrySelected(newCountrySelect);
                    }}
                />
            )}
            {cities && (
                <Autocomplete
                    value={citySelected}
                    options={cities}
                    renderInput={(params) => <TextField {...params} />}
                    isOptionEqualToValue={(option, value) => option.value === value.value}
                    sx={{width: 400}}
                    onChange={(
                        event: any,
                        newCitySelect: { value: number; label: string } | null
                    ) => {
                        setCitySelected(newCitySelect);
                    }}
                />
            )}
        </>
    );
};
