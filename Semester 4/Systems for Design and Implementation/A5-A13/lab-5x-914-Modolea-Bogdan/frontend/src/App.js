import './App.css';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import Navbar from './layout/Navbar.js';
import Home from './pages/Home.js';
import AllTeams from './team/AllTeams.js';
import AddTeam from './team/AddTeam.js';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import EditTeam from './team/EditTeam';
import ViewTeam from './team/ViewTeam';
import AllLeagues from './league/AllLeagues';
import AddLeague from './league/AddLeague';
import ViewLeagut from './league/ViewLeague';
import EditLeague from './league/EditLeague';
import AllFans from './fan/AllFans';
import AddFan from './fan/AddFan';
import EditFan from './fan/EditFan';
import ViewFan from './fan/ViewFan';
import FilterFans from './fan/FilterFans';
import StatisticalReport from './league/StatisticalReport';

function App() {
  // const toAddURL = 'lab-5x-914-Modolea-Bogdan/';
  const toAddURL = '';
  const serverLink = 'http://localhost:8080/';
    // const serverLink = '';
  
  return (
    <div className="App">
      <Router>
        <Navbar />

        <Routes>
          <Route exact path={"/" + toAddURL} element={<Home />}></Route>
          <Route path={"/" + toAddURL +  "teams"} element={<AllTeams />}></Route>
          <Route exact path={"/" + toAddURL +  "addTeam"} element={<AddTeam />}></Route>
          <Route exact path={"/" + toAddURL + "updateTeam/:id"} element={<EditTeam />}></Route>
          <Route exact path={"/" + toAddURL + "viewTeam/:id"} element={<ViewTeam />}></Route>
          <Route exact path="/leagues" element={<AllLeagues />}></Route>
          <Route exact path="/addLeague" element={<AddLeague />}></Route>
          <Route exact path={"/" + toAddURL + "updateLeague/:id"} element={<EditLeague />}></Route>
          <Route exact path={"/" + toAddURL + "viewLeague/:id"} element={<ViewLeagut />}></Route>
          <Route exact path={"/" + toAddURL + "statisticalReport"} element={<StatisticalReport />}></Route>
          <Route exact path="/fans" element={<AllFans />}></Route>
          <Route exact path="/addFan" element={<AddFan />}></Route>
          <Route exact path={"/" + toAddURL + "updateFan/:id"} element={<EditFan />}></Route>
          <Route exact path={"/" + toAddURL + "viewFan/:id"} element={<ViewFan />}></Route>
          <Route exact path={"/" + toAddURL + "filterFans/:age"} element={<FilterFans />}></Route>
        </Routes>
      </Router>
    </div>
  );
}

export default App;
