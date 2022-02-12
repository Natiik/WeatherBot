import React, {useState} from 'react'

export const Question = () => {
    const [help, setHelp] = useState<string>()
    return (
        <>
            <button onMouseEnter={event => {
                setHelp("To get your Id send /id command to WeatherBot")
            }}
                    onMouseLeave={event => {
                        setHelp("")
                    }} >
                ?
            </button>
                {help}
        </>
    )
}