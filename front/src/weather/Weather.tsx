import React, {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import {Description} from "./Description";
import {BACKEND_URL} from "../Properties";

export const Weather = () => {
    const [weather, setWeather] = useState<any>();
    const head = {
        method: "GET",
        headers: {id: localStorage.getItem('id') + ""},
    };
    const navigate = useNavigate();

    useEffect(() => {
        fetch(`${BACKEND_URL}/weather`, head)
            .then((response) => {
                if (response.status === 200) {
                    (response.json()).then((json) => {
                        setWeather(json)
                    })
                } else {
                    setWeather('ERROR')
                }
            })
    }, [])

    return (
        <>{weather ?
            (<><Description weather={weather}/>
                    <button onClick={() => {
                        navigate('/cabinet')
                    }}>
                        Back
                    </button>
                </>
            ) : (<span>Loading...</span>)
        }
        </>
    )
}