import React, { useEffect, useState } from "react";
import Autocomplete from "@mui/material/Autocomplete";
import TextField from "@mui/material/TextField";

export const Languages = (props: { setter: (language: string) => void }) => {
  const languages = [
    { value: "EN", label: "English" },
    { value: "UA", label: "Українська" },
    { value: "RU", label: "Русский" },
  ];

  const [languageSelected, setLanguageSelected] = useState<{
    value: string;
    label: string
  } | null>(null);

  useEffect(() => {
    if (languageSelected === null) {
      return;
    }
    props.setter(languageSelected.value);
  }, [languageSelected]);
  return (
    <>
      <div>Language</div>
      <Autocomplete
        value={languageSelected}
        options={languages}
        sx={{ width: 300 }}
        renderInput={(params) => <TextField {...params} />}
        isOptionEqualToValue={(option, value)=> option.value === value.value}
        onChange={(
          event: any,
          newLanguageSelected: { value: string; label: string } | null
        ) => {
          setLanguageSelected(newLanguageSelected);
        }}
      />
    </>
  );
};
