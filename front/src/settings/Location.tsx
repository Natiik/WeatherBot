import React, { useEffect, useState } from "react";
import Autocomplete from "@mui/material/Autocomplete";
import TextField from "@mui/material/TextField";

export const Location = (props: { setter: (id: number) => void }) => {
  const [countries, setCountries] = useState<
    { value: string; label: string }[] | null
  >(null);
  const [countrySelected, setCountrySelected] = useState<{
    value: string;
    label: string;
  } | null>(null);
  const [cities, setCities] = useState<
    { value: number; label: string }[] | null
  >(null);
  const [citySelected, setCitySelected] = useState<{
    value: number;
    label: string;
  } | null>(null);

  useEffect(() => {
    getCountries();
  }, []);

  useEffect(() => {
    getCities();
  }, [countrySelected]);

  useEffect(()=>{
    if(citySelected===null){
      return
    }
    props.setter(citySelected.value)
  },[citySelected])

  const getCountries = () => {
    fetch("http://localhost:8080/location/en")
      .then((response) => {
        return response.json();
      })
      .then((json) => {
        const result = json.map((country: any) => {
          return {
            value: country.shortName,
            label: country.fullName,
          };
        });
        setCountries(result);
      });
  };

  const getCities = () => {
    if (countrySelected === null) {
      return;
    }
    fetch("http://localhost:8080/location/en/" + countrySelected.value)
      .then((response) => response.json())
      .then((json) => {
        const result = json.map((city: any) => {
          return {
            value: city.id,
            label: city.name,
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
          renderInput={(params) => <TextField {...params} />}
          options={countries}
          sx={{ width: 200 }}
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
          renderInput={(params) => <TextField {...params} />}
          options={cities}
          sx={{ width: 200 }}
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
