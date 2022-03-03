import React from 'react';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import {Cabinet} from "./cabinet/Cabinet";
import {Start} from "./index/Start";
import {Weather} from "./weather/Weather";
import {Settings} from "./settings/Settings";


function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path='/cabinet' element={<Cabinet/>}/>
                <Route path='/start' element={<Start/>}/>
                <Route path='/weather' element={<Weather/>}/>
                <Route path='/settings' element={<Settings/>}/>
            </Routes>
        </BrowserRouter>
    );

}

export default App;
