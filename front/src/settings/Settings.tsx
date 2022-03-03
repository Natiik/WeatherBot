import React, {useEffect, useState} from 'react';
import TextField from '@mui/material/TextField';
import Autocomplete from '@mui/material/Autocomplete';
import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormControl from '@mui/material/FormControl';


export function Settings() {
    const languages = [
        {value: 'english', label: 'English'},
        {value: 'ukrainian', label: 'Українська'},
        {value: 'russian', label: 'Русский'},
    ];

    const [countries, setCountries] = useState();

    const getCountries = () => {
        fetch('http://localhost:8080/location/en')
            .then((response) => {
                    return response.json()
                }
            )
            .then((json) => {
                console.log(json);
               const result = json.map((country: any) => {
                    return {
                        value: country.shortName,
                        label: country.fullName
                    }
                })
                setCountries(result)
            })
    }

    useEffect(() => {
        getCountries()
    }, [])

    return (
        <div>
            <div>Language</div>
            <Autocomplete
                options={languages}
                sx={{width: 300}}
                renderInput={(params) => <TextField {...params} />}
            />
            <div>Metrics</div>
            <FormControl>
                <RadioGroup row>
                    <FormControlLabel value={"standard"} control={<Radio/>} label={"Standard"}/>
                    <FormControlLabel value={"metric"} control={<Radio/>} label={"Metric"}/>
                    <FormControlLabel value={"imperial"} control={<Radio/>} label={"Imperial"}/>
                </RadioGroup>
            </FormControl>
            <div>Location</div>
            {countries&&<Autocomplete
                renderInput={(params) => <TextField{...params}/>}
                options={countries}
                sx={{width: 200}}
            />}
        </div>
    );
}