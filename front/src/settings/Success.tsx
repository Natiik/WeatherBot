import React from "react";
import {useNavigate} from "react-router-dom";

export const Success = () => {
    const navigate=useNavigate();
    return <>
        <div>SUCCESS</div>
        <button onClick={()=>{navigate('/cabinet')}}>
            Menu
        </button>
    </>;
};
