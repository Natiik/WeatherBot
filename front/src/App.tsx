import React from 'react';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import {Cabinet} from "./cabinet/Cabinet";
import {Start} from "./index/Start";


function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path='/cabinet' element={<Cabinet/>}/>
                <Route path='/start' element={<Start/>}/>
            </Routes>
        </BrowserRouter>
    );

}

export default App;
