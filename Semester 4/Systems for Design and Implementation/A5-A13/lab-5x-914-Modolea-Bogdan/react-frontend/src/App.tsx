import LeagueShowAll from './components/league/LeagueShowAll'
import React from 'react'
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
import AppMenu from './components/AppMenu'
import AppHome from './components/AppHome'

function App() {
  return (
    <React.Fragment>
      <Router>
        <AppMenu />

        <Routes>
          <Route path="/" element={<AppHome />} />
          <Route path="/leagues" element={<LeagueShowAll />} />
        </Routes>

      </Router>
    </React.Fragment>
  );
}

export default App;
