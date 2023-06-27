import axios from 'axios';
import React, { useState } from 'react'
import { Link } from 'react-router-dom'
import * as myConstClass from '../constants/constants';

export default function Navbar() {

  const [role, setRole] = useState('');

  const getRoles = async() => {
    const data = JSON.parse(localStorage.getItem('login'));
    const username = await axios.get(`${myConstClass.SERVER_LINK}/user/getUsername/${data.store}`);
    const result = await axios.get(`${myConstClass.SERVER_LINK}/user/${username.data}/role`);
    setRole(result.data);
  }

  getRoles();

  return (
    <div>
        <nav className="navbar navbar-expand-lg navbar-light navbar-dark bg-primary">
            <div className="container-fluid">
                <Link className="navbar-brand" to={"/"}>Esports Manager</Link>
                <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>

                <Link className='btn btn-outline-light' to={"/teams"}>Teams</Link>
                <Link className='btn btn-outline-light' to={"/leagues"}>Leagues</Link>
                <Link className='btn btn-outline-light' to={"/fans"}>Fans</Link>

                {  role === 'ROLE_ADMIN' ?
                    <Link className='btn btn-outline-light' to={"/users"}>Users</Link>
                    : null
                }
                <Link className='btn btn-outline-light' to={"/chat"}>All Chat</Link>
            </div>
        </nav>
    </div>
  )
}
