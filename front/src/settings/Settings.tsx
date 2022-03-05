import React, {useState} from "react";
import { Languages } from "./Languages";
import { Metrics } from "./Metrics";
import { Location } from "./Location";
import {Button} from "@mui/material";


export const Settings = () => {
    const[language, setLanguage]=useState<string>();
    const[locationId, setLocationId]=useState<number>();
    const[metrics, setMetrics]=useState<string>();


    const press =()=>{
        console.log({})
    }
  return (
    <>
      <Languages setter={setLanguage} />
      <Metrics setter={setMetrics} />
      <Location setter={setLocationId} />
        <Button onClick={()=>console.log(locationId, language)}>Click me</Button>
    </>
  );
};
