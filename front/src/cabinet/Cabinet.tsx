import React, {useEffect} from "react";
import {useNavigate} from "react-router-dom";

export const Cabinet = () => {
    const navigate = useNavigate();
    useEffect(() => {
        if (!localStorage.getItem('id')) {
            navigate('/start')
        }
    },[])

    return (
        <>
            <div>
                Nice to see you here! <br/> Choose what to do:
            </div>
            <button onClick={()=>{}}>
                Get weather
            </button>
            <button>
                Settings
            </button>
        </>
    )
}