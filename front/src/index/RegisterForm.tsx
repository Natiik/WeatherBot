import React, { useState } from "react";
import {useNavigate, useRoutes} from "react-router-dom";

export const RegisterForm = () => {
  const [id, setId] = useState<number>();
  const navigate=useNavigate();

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
          localStorage.setItem("id", id + "");
          setId(0);
          navigate("/cabinet")
        }}
      >
        Send
      </button>
    </>
  );
};
