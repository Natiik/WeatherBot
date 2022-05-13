import React, {useEffect, useState} from "react";
import {Languages} from "./Languages";
import {Metrics} from "./Metrics";
import {Location} from "./Location";
import {useNavigate} from "react-router-dom";
import axios from "axios";
import {BACKEND_URL} from "../Properties";

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
            .post(`${BACKEND_URL}/update`, {
                id: localStorage.getItem("id"),
                location: locationId,
                language: language,
                metrics: metrics,
            })
            .then((response) => {
                localStorage.setItem("setting_status", response.status.toString())
                navigate("/change")
            })
            .catch((response) => {
                localStorage.setItem("setting_status", response.response.status)
                navigate("/change")
            });
    };

    useEffect(() => {
        fetch(`${BACKEND_URL}/init_settings/${localStorage.getItem("id")}`)
            .then((response) => response.json())
            .then((json) => {
                setInitValues(json);
            });
    }, []);

    return (
        <>
            {initValues ? (
                <>
                    <Languages setter={setLanguage} init={initValues.language}/>
                    <Metrics setter={setMetrics} init={initValues.metrics}/>
                    <Location setter={setLocationId} init={initValues.location}/>
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
