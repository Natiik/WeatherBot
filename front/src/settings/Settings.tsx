import React, {useEffect} from 'react';
import TextField from '@mui/material/TextField';
import Autocomplete from '@mui/material/Autocomplete';
import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormControl from '@mui/material/FormControl';

// import countryNames from './countries.json';


export function Settings() {
    const options = [
        {value: 'english', label: 'English'},
        {value: 'ukrainian', label: 'Українська'},
        {value: 'russian', label: 'Русский'},
    ];

    const getCountries = () => {
        fetch('./front/countries.json', {})
            .then(function (response) {
                console.log(response)
                return response.json();
            })
            .then(function (myJson) {
                console.log(myJson);
            })
    }

    useEffect(() => {
        getCountries()
    }, [])

    return (
        <div>
            <div>Language</div>
            <Autocomplete
                options={options}
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
            <Autocomplete
                renderInput={(params) => <TextField{...params}/>}
                options={options}
                sx={{width: 200}}
            />
        </div>
    );
}