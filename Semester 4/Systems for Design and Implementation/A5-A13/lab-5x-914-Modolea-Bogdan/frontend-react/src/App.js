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
import NumberNationalities from './fan/NumberNationalities';
import AddTeamToFan from './fan/AddTeamToFan';
import Login from './auth/Login';
import Register from './auth/Register';
import User from './user/User';
import AllUsers from './user/AllUsers';
import EditUser from './user/EditUser';
import ChatRoom from './chat/ChatRoom';
import ChartFans from './fan/ChartFans';

function App() {
  return (
    <div className="App">
      <Router>
        <Navbar />

        <Routes>
          <Route exact path={"/"} element={<Home />}></Route>
          <Route path={"/teams"} element={<AllTeams />}></Route>
          <Route exact path={"/addTeam"} element={<AddTeam />}></Route>
          <Route exact path={"/updateTeam/:id"} element={<EditTeam />}></Route>
          <Route exact path={"/viewTeam/:id"} element={<ViewTeam />}></Route>
          <Route exact path="/leagues" element={<AllLeagues />}></Route>
          <Route exact path="/addLeague" element={<AddLeague />}></Route>
          <Route exact path={"/updateLeague/:id"} element={<EditLeague />}></Route>
          <Route exact path={"/viewLeague/:id"} element={<ViewLeagut />}></Route>
          <Route exact path={"/statisticalReport"} element={<StatisticalReport />}></Route>
          <Route exact path="/fans" element={<AllFans />}></Route>
          <Route exact path="/addFan" element={<AddFan />}></Route>
          <Route exact path={"/updateFan/:id"} element={<EditFan />}></Route>
          <Route exact path={"/viewFan/:id"} element={<ViewFan />}></Route>
          <Route exact path={"/filterFans/:age"} element={<FilterFans />}></Route>
          <Route exact path={"/nationalities"} element={<NumberNationalities/>}></Route>
          <Route exact path={"/addTeamToFan/:fid"} element={<AddTeamToFan />}></Route>
          <Route exact path={"/login"} element={<Login />}></Route>
          <Route exact path={"/register"} element={<Register />}></Route>
          <Route exact path={"/user/:username"} element={<User />}></Route>
          <Route exact path={"/users"} element={<AllUsers />}></Route>
          <Route exact path={"/editUser/:username"} element={<EditUser />}></Route>
          <Route exact path={"/chat"} element={<ChatRoom />}></Route>
          <Route exact path={"/predictFans"} element={<ChartFans />}></Route>
        </Routes>
      </Router>
    </div>
  );
}

export default App;
