import React from 'react'
import { Link, useNavigate } from 'react-router-dom'
import axios from 'axios';
import * as myConstClass from '../constants/constants';

export default function Home() {

  let navigate = useNavigate();

  const logout = () => {
    localStorage.clear();
    alert('You logged out!');
    window.location.reload();
  }

  const myProfile = async() => {
    const token = JSON.parse(localStorage.getItem('login')).store;
    const svLink = myConstClass.SERVER_LINK;
    const user = await axios.get(`${svLink}/user/getUsername/${token}`);
    navigate("/user/" + user.data);
  }

  return (
    <div>
      <h3>
          This is the home page. You can use this page to select a page to view.
      </h3>
      { 
        (JSON.parse(localStorage.getItem("login")) == null || !JSON.parse(localStorage.getItem("login")).login) ?
        <div>
          <Link className='btn btn-primary my-2' to={"/login"}>Login</Link>
          <h1></h1>
          <Link className='btn btn-primary my-2' to={"/register"}>Register</Link>
          <h1></h1>
        </div>

         :
          <div>
            <button onClick={myProfile} className='btn btn-primary my-2'>My profile</button> <br></br>
            <button onClick={logout} className='btn btn-primary my-2'>Logout</button>
          </div>
    } 
    </div>
  )
}
