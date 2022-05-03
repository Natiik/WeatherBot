import React from "react";

export const Description = ({weather}: { weather: any }) => {

    return (
        <>
            {weather !== 'ERROR' ?
                (<div>
                    location: {weather?.name}<br/>
                    update: {weather?.update} <br/>
                    state: {weather?.state}<br/>
                    temperature: {weather?.temperature}<br/>
                    feels like: {weather?.feelsLike}<br/>
                    humidity: {weather?.humidity}<br/>
                    pressure: {weather?.pressure}<br/>
                    wind speed: {weather?.windSpeed} m/s<br/>
                    sunrise: {weather?.sunrise}<br/>
                    sunset: {weather?.sunset}<br/>
                </div>) :
                (<div>
                    ERROR
                </div>)}
        </>
    )
}