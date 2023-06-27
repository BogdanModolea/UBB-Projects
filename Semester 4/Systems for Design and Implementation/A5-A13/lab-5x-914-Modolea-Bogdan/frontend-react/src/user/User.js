import axios from 'axios';
import React, {useEffect, useState} from 'react'
import { Link, useParams } from 'react-router-dom'
import { ToastContainer, toast } from 'react-toastify';
import * as myConstClass from '../constants/constants';
import Loading from '../constants/Loading';

export default function User() {

    const [user, setUser] = useState({
        name: '',
        age: 0,
        email: '',
        location: '',
        roles: ''
    });
    const[loading, setLoading] = useState(true);

    const [counters, setCounters] = useState({
        fans: 0,
        leagues: 0,
        teams: 0
    });

    const {username} = useParams();

    const[currentUser, setCurrentUser] = useState('');
    const[currentRole, setCurrentRole] = useState('');
    const[currentPages, setCurrentPages] = useState(0);

    useEffect(() => {
        loadUser();
    }, []);

    const loadUser = async()=>{
        setLoading(true);
        if(localStorage.getItem('login') !== null){
            const token = JSON.parse(localStorage.getItem('login')).store;
            const myUser = await axios.get(`${myConstClass.SERVER_LINK}/user/getUsername/${token}`);
            const myRole = await axios.get(`${myConstClass.SERVER_LINK}/user/${myUser.data}/role`);
            
            setCurrentUser(myUser.data);
            setCurrentRole(myRole.data);
        }

        const result = await axios.get(`${myConstClass.SERVER_LINK}/user/${username}`);
        const cntL = await axios.get(`${myConstClass.SERVER_LINK}/user/${username}/leagues`);
        const cntT = await axios.get(`${myConstClass.SERVER_LINK}/user/${username}/teams`);
        const cntF = await axios.get(`${myConstClass.SERVER_LINK}/user/${username}/fans`);
        counters.leagues = cntL.data;
        counters.teams = cntT.data;
        counters.fans = cntF.data;
        setUser(result.data);
        setCurrentPages(result.data.recordsOnPage);
        setLoading(false);
    }

    const onInputChange=(e)=>{
        setCurrentPages(e.target.value);
    }

    const updatePages = async()=>{
        try{
            const token = JSON.parse(localStorage.getItem('login')).store;
            console.log(token);

            await axios.put(`${myConstClass.SERVER_LINK}/user/update/${username}/pages/${currentPages}`, {
                headers: {
                    'Authorization': `Bearer ${token}`,
                }
            });
        }
        catch(err){
            console.log(err.message);
            toast.error('Error: ' + err.message, {
                position: "top-right",
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                theme: "light",
            });
            return;
        }
    }

  return (
    <div>
         {
            !loading ?
                <div className='container'>
                    <div className='row'>
                        <div className='col-md-6 offset-md-3 border rounded p-4 mt-2 shadow'>
                            <h2 className='text-center m-4'>User Details</h2>
                            <div className='card'>
                                <div className='card-header'>
                                    <ul className='list-group list-group-flush'>
                                        <li className='list-group-item'><b>Name: {user.name}</b></li>
                                        <li className='list-group-item'><b>Age: {user.email}</b></li>
                                        <li className='list-group-item'><b>Role: {user.roles}</b></li>
                                        <li className='list-group-item'><b>Age: {user.age}</b></li>
                                        <li className='list-group-item'><b>Location: {user.location}</b></li>
                                        <li className='list-group-item'><b>Teams added: {counters.teams}</b></li>
                                        <li className='list-group-item'><b>Leagues added: {counters.leagues}</b></li>
                                        <li className='list-group-item'><b>Fans added: {counters.fans}</b></li>
                                    </ul>
                                </div>
                            </div>
                            <Link className='btn btn-primary my-2' to={"/"}>Back</Link><br></br>
                            {
                                currentUser === user.name || currentRole === 'ROLE_ADMIN' ?
                                    <div>
                                        <div className='mb-3'>
                                            <label htmlFor='currentPages' className='form-label'>Current Pages</label>
                                            <input type={'number'} className='form-control' name='currentPages' placeholder='Enter Pages' value={currentPages} onChange={(e)=>onInputChange(e)}/>
                                        </div>
                                        <button onClick={updatePages} className='btn btn-primary my-2'>Edit Pages</button>
                                        <ToastContainer />
                                    </div>
                                :

                                null
                            }
                        </div>
                    </div>
                </div>
            :
                <Loading />
        }
    </div>
  )
}
