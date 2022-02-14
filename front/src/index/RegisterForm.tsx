import React, {useState} from "react";
import {useNavigate} from "react-router-dom";

export const RegisterForm = () => {
    const [id, setId] = useState<number>(0 );
    const navigate = useNavigate();

    function registration() {
        localStorage.setItem("id", id + "");
        setId(0);
        navigate("/cabinet");
    }

    return (
        <>
            <input
                value={id !== 0 ? id : ""}
                placeholder={"Put it here"}
                onChange={(change) =>
                    setId(!isNaN(+change.target.value) ? +change.target.value : id)
                }
            />
            <button
                onClick={() => {
                    id !== 0 ? registration() : alert("Type your id");
                }}
            >
                Send
            </button>
        </>
    );
};
