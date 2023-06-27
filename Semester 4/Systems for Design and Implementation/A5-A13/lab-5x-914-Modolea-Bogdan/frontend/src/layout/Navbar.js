import React from 'react'
import { Link } from 'react-router-dom'

export default function Navbar() {
  // const toAddURL = 'lab-5x-914-Modolea-Bogdan/';
  const toAddURL = '';
  const serverLink = 'http://localhost:8080/';
    // const serverLink = '';

  return (
    <div>
        <nav className="navbar navbar-expand-lg navbar-light navbar-dark bg-primary">
            <div className="container-fluid">
                <Link className="navbar-brand" to={"/" + toAddURL}>Esports Manager</Link>
                <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>

                <Link className='btn btn-outline-light' to={"/" + toAddURL + "teams"}>Teams</Link>
                <Link className='btn btn-outline-light' to={"/" + toAddURL + "leagues"}>Leagues</Link>
                <Link className='btn btn-outline-light' to={"/" + toAddURL + "fans"}>Fans</Link>
            </div>
        </nav>
    </div>
  )
}
