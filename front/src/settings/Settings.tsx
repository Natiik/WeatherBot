import React, { useState } from "react";
import { Languages } from "./Languages";
import { Metrics } from "./Metrics";
import { Location } from "./Location";
import { Button } from "@mui/material";
import axios from "axios";

export const Settings = () => {
  const [language, setLanguage] = useState<string>();
  const [locationId, setLocationId] = useState<number>();
  const [metrics, setMetrics] = useState<string>();

  const press = () => {
    axios.post("http://localhost:8080/update", {
      id: localStorage.getItem("id"),
      location: locationId,
      language: language,
      metrics: metrics,
    }).then((response)=>{console.log(response.status)});
  };
  return (
    <>
      <Languages setter={setLanguage} />
      <Metrics setter={setMetrics} />
      <Location setter={setLocationId} />
      <Button onClick={() => {press()}}>Click me</Button>
    </>
  );
};
