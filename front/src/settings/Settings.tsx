import React, { useEffect, useState } from "react";
import { Languages } from "./Languages";
import { Metrics } from "./Metrics";
import { Location } from "./Location";
import { useNavigate } from "react-router-dom";
import axios from "axios";

export const Settings = () => {
  const navigate = useNavigate();
  const [language, setLanguage] = useState<string>();
  const [locationId, setLocationId] = useState<number>();
  const [metrics, setMetrics] = useState<string>();
  const [initValues, setInitValues] = useState<{
    language: { value: string; label: string };
    location: {
      country: { value: string; label: string };
      city: { label: string; value: number };
    };
    metrics: string;
  }>();

  const press = () => {
    axios
      .post("http://localhost:8080/update", {
        id: localStorage.getItem("id"),
        location: locationId,
        language: language,
        metrics: metrics,
      })
      .then((response) => {
          if(response.status==200){
              navigate("change/success")
          }
        console.log(response.status);
      }); //TODO
  };

  useEffect(() => {
    fetch("http://localhost:8080/init_settings/" + localStorage.getItem("id"))
      .then((response) => response.json())
      .then((json) => {
        setInitValues(json);
      });
  }, []);

  return (
    <>
      {initValues ? (
        <>
          <Languages setter={setLanguage} init={initValues.language} />
          <Metrics setter={setMetrics} init={initValues.metrics} />
          <Location setter={setLocationId} init={initValues.location} />
          <button
            onClick={() => {
              press();
            }}
          >
            Submit
          </button>
          <button
            onClick={() => {
              navigate("/cabinet");
            }}
          >
            Back
          </button>
        </>
      ) : (
        <></>
      )}
    </>
  );
};
