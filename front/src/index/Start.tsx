import React, {useEffect} from "react";
import {Greeting} from "./Greeting";
import {RegisterForm} from "./RegisterForm";
import {Question} from "./Question";
import {useNavigate} from "react-router-dom";

export const Start = () => {
    const navigate = useNavigate();
    useEffect(() => {
        if (localStorage.getItem('id')) {
            navigate('/cabinet')
        }
    },[])

    return <>
        <Greeting/>
        <RegisterForm/>
        <Question/>
    </>
}