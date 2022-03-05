import React, {useState} from "react";
import FormControl from "@mui/material/FormControl";
import RadioGroup from "@mui/material/RadioGroup";
import FormControlLabel from "@mui/material/FormControlLabel";
import Radio from "@mui/material/Radio";

export const Metrics = (props: { setter: (metric: string) => void }) => {
  const metrics = [
    { value: "STANDARD", label: "Standard" },
    { value: "METRIC", label: "Metrics" },
    { value: "IMPERIAL", label: "Imperial" },
  ];

  const [metricsSelected, setMetricsSelected]=useState<{value: string; label: string } | null>(null)
  return (
    <>
      <div>Metrics</div>
      <FormControl>
        <RadioGroup
          row
        >
          {metrics.map((metric, index) => (
            <FormControlLabel
              key={index}
              control={<Radio />}
              value={metric.value}
              label={metric.label}
            />
          ))}
        </RadioGroup>
      </FormControl>
    </>
  );
};
