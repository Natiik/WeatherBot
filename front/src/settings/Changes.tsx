import React from "react";
import {useNavigate} from "react-router-dom";

export const Changes = () => {
    const navigate = useNavigate();
    return (
        <>
            {localStorage.getItem("setting_status") === '200' ?
                (<div>SUCCESS</div>) :
                (<>
                    <div> FAILED TO CHANGE SETTINGS: ERROR {localStorage.getItem("setting_status")} </div>
                    <button onClick={() => {
                        navigate('/settings')
                    }}>
                        Back
                    </button>
                </>)
            }
            <button onClick={() => {
                navigate('/cabinet')
            }}>
                Menu
            </button>
        </>
    )
}