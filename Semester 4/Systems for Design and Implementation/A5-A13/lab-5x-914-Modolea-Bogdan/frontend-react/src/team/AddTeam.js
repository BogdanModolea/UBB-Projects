import axios from 'axios';
import React, { useState } from 'react'
import { useNavigate, Link } from 'react-router-dom';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import * as myConstClass from '../constants/constants';

export default function AddTeam() {

    let navigate = useNavigate();
    
    const[league, setLeague] = useState('');

    const [team, setTeam] = useState({
        name: '',
        top: '',
        jungle: '',
        mid: '',
        bot: '',
        support: '',
        leagueID: 0,
        username: ''
    })

    const{name, top, jungle, mid, bot, support, username} = team;

    const onInputChange=(e)=>{
        setTeam({...team, [e.target.name]: e.target.value});
    }

    const onInputChange2=(e)=>{
        setLeague(e.target.value);
    }
        
    const onSubmit=async(e)=>{
        e.preventDefault();
      
        try{
            const result = await axios.get(`${myConstClass.SERVER_LINK}/leagues/get-league-by-name/${league}`);
            team.leagueID = result.data.lid;
            console.log(result.data);

            if(result.data === ''){
                toast.warn('League not found!', {
                    position: "top-right",
                    autoClose: 3000,
                    hideProgressBar: false,
                    closeOnClick: true,
                    pauseOnHover: true,
                    draggable: true,
                    progress: undefined,
                    theme: "light",
                });
                return;
            }
        }
        catch(err){
            toast.warn('League not found!', {
                position: "top-right",
                autoClose: 3000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                theme: "light",
            });
            return;
        }

        if(team.name === '') { 
            toast.warn('Name cannot be left empty!', {
                position: "top-right",
                autoClose: 3000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                theme: "light",
                });
            return;
        }
        else if(team.top === '') {
            toast.warn('Toplaner cannot be left empty!', {
                position: "top-right",
                autoClose: 3000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                theme: "light",
                });
            return;
        }
        else if(team.jungle === '') {
            toast.warn('Jungler cannot be left empty!', {
                position: "top-right",
                autoClose: 3000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                theme: "light",
                });
            return;
        }
        else if(team.mid === '') {
            toast.warn('Midlaner cannot be left empty!', {
                position: "top-right",
                autoClose: 3000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                theme: "light",
                });
            return;
        }
        else if(team.bot === '') {
            toast.warn('Botlaner cannot be left empty!', {
                position: "top-right",
                autoClose: 3000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                theme: "light",
                });
            return;
        }
        else if(team.support === '') {
            toast.warn('Support cannot be left empty!', {
                position: "top-right",
                autoClose: 3000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                theme: "light",
                });
            return;
        }
        else if(league === '') {
            toast.warn('League cannot be left empty!', {
                position: "top-right",
                autoClose: 3000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                theme: "light",
                });
            return;
        }

        if(!team.leagueID) {
            toast.warn('League not found!', {
                position: "top-right",
                autoClose: 3000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                theme: "light",
                });
            return;
        }


        try{
            const token = JSON.parse(localStorage.getItem('login')).store;
            console.log(token);
            const currentUsername = await axios.get(`${myConstClass.SERVER_LINK}/user/getUsername/${token}`);
            console.log(currentUsername.data);

            team.username = currentUsername.data;

            console.log(team);

            await axios.post(`${myConstClass.SERVER_LINK}/teams`, team, {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });
            navigate("/teams");
        }
        catch(err){
            toast.error('Something went wrong!', {
                position: "top-right",
                autoClose: 3000,
                hideProgressBar: false,
                closeOnClick: true,
                draggable: true,
                progress: undefined,
                theme: "light",
            });
            return;
        }
    };

  return (
    <div className='container'>
        <div className='row'>
            <div className='col-md-6 offset-md-3 border rounded p-4 mt-2 shadow'>
                <h2 className='text-center m-4'>Add Team</h2>

                <form onSubmit={(e)=>onSubmit(e)}>
                    <div className='mb-3'>
                        <label htmlFor='name' className='form-label'>Team Name</label>
                        <input type={'text'} className='form-control' name='name' placeholder='Enter Team Name' value={name} onChange={(e)=>onInputChange(e)}/>
                    </div>
                    <div className='mb-3'>
                        <label htmlFor='top' className='form-label'>Toplaner Name</label>
                        <input type={'text'} className='form-control' name='top' placeholder='Enter Toplaner Name' value={top} onChange={(e)=>onInputChange(e)}/>
                    </div>
                    <div className='mb-3'>
                        <label htmlFor='jungle' className='form-label'>Jungler Name</label>
                        <input type={'text'} className='form-control' name='jungle' placeholder='Enter Jungler Name' value={jungle} onChange={(e)=>onInputChange(e)}/>
                    </div>
                    <div className='mb-3'>
                        <label htmlFor='mid' className='form-label'>Midlaner Name</label>
                        <input type={'text'} className='form-control' name='mid' placeholder='Enter Midlaner Name' value={mid} onChange={(e)=>onInputChange(e)}/>
                    </div>
                    <div className='mb-3'>
                        <label htmlFor='bot' className='form-label'>Botlaner Name</label>
                        <input type={'text'} className='form-control' name='bot' placeholder='Enter Botlaner Name' value={bot} onChange={(e)=>onInputChange(e)}/>
                    </div>
                    <div className='mb-3'>
                        <label htmlFor='support' className='form-label'>Support Name</label>
                        <input type={'text'} className='form-control' name='support' placeholder='Enter Support Name' value={support} onChange={(e)=>onInputChange(e)}/>
                    </div>
                    <div className='mb-3'>
                        <label htmlFor='league' className='form-label'>League Name</label>
                        <input type={'text'} className='form-control' name='league' placeholder='Enter League Name' value={league} onChange={(e)=>onInputChange2(e)}/>
                    </div>

                    <button type='submit' className='btn btn-outline-primary'>Add Team</button>
                    <Link className='btn btn-outline-danger mx-2' to={"/teams"}>Cancel</Link>
                    <ToastContainer />
                </form>
            </div> 
        </div>
    </div>
  )
}
