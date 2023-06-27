import axios from 'axios';
import React, { useEffect, useState } from 'react'
import { useNavigate, Link, useParams } from 'react-router-dom';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import * as myConstClass from '../constants/constants';

export default function EditTeam() {

    let navigate = useNavigate();

    const {id} = useParams()

    const [team, setTeam] = useState({
        name: '',
        top: '',
        jungle: '',
        mid: '',
        bot: '',
        support: ''
    })

    const{name, top, jungle, mid, bot, support} = team;

    const onInputChange=(e)=>{
        setTeam({...team, [e.target.name]: e.target.value});
    }

    useEffect(()=>{
        loadTeam();
    }, []);

    const onSubmit=async(e)=>{
        e.preventDefault();

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

        try{
            const token = JSON.parse(localStorage.getItem('login')).store;
            await axios.put(`${myConstClass.SERVER_LINK}/teams/${id}`, team, {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });
            navigate("/teams");
        }
        catch(err){
            console.log(err);
            toast.error('Error editing team!', {
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
    };

    const loadTeam = async()=>{
        const result = await axios.get(`${myConstClass.SERVER_LINK}/teams/${id}`);
        setTeam(result.data);
    }

  return (
    <div className='container'>
        <div className='row'>
            <div className='col-md-6 offset-md-3 border rounded p-4 mt-2 shadow'>
                <h2 className='text-center m-4'>Edit Team</h2>

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
                    <button type='submit' className='btn btn-outline-primary'>Update Team</button>
                    <Link className='btn btn-outline-danger mx-2' to={"/teams"}>Cancel</Link>
                    <ToastContainer />
                </form>
            </div> 
        </div>
    </div>
  )
}
