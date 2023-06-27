import axios from 'axios';
import React, { useState } from 'react'
import { useNavigate, Link, useParams } from 'react-router-dom';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import * as myConstClass from '../constants/constants';

export default function AddTeamToFan() {
    let navigate = useNavigate();

    const [fan, setFan] = useState({
        fid: 0,
        fanSince: 2011,
        opinion: ''
    });

    const[teamName, setTeamName] = useState('');
    const[teamID, setTeamID] = useState(0);
    fan.fid = useParams().fid;

    const{fid, fanSince, opinion} = fan;

    const onInputChange=(e)=>{
        setFan({...fan, [e.target.name]: e.target.value});
    }

    const onInputChange2 = (e) => {
        setTeamName(e.target.value);
    }

    const getTeam = async(teamName) => {
        const result = await axios.get(`${myConstClass.SERVER_LINK}/teams/getTeamByName/${teamName}`);
        setTeamID(result.data.tid);
    }

    const onSubmit=async(e)=>{
        e.preventDefault();

        if(fan.fanSince < 2010 || fan.fanSince > 2023) {
            toast.warn('Cannot have a fan of invalid ranges for teams!', {
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
        else if(fan.opinion === '') {
            toast.warn('Opinion cannot be left empty!', {
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
        else if(teamName === '') {
            toast.warn('Team name cannot be left empty!', {
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
            getTeam(teamName);
            await axios.post(`${myConstClass.SERVER_LINK}/teams/${teamID}/fans`, fan);
            navigate("/fans");
        }
        catch(err) {
            toast.warn('Something went wrong! Try again!', {
                position: "top-right",
                autoClose: 3000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                theme: "light",
            });
        }
    };

  return (
    <div className='container'>
        <div className='row'>
            <div className='col-md-6 offset-md-3 border rounded p-4 mt-2 shadow'>
                <h2 className='text-center m-4'>Add Fan</h2>

                <form onSubmit={(e)=>onSubmit(e)}>
                    <div className='mb-3'>
                        <label htmlFor='teamName' className='form-label'>Their Team</label>
                        <input type={'text'} className='form-control' name='teamName' placeholder='Their Team Name' value={teamName} onChange={(e)=>onInputChange2(e)}/>
                    </div>
                    <div className='mb-3'>
                        <label htmlFor='fanSince' className='form-label'>Fan Since Year</label>
                        <input type={'number'} className='form-control' name='fanSince' placeholder='Enter Year' value={fanSince} onChange={(e)=>onInputChange(e)}/>
                    </div>
                    <div className='mb-3'>
                        <label htmlFor='opinion' className='form-label'>Their Opinion</label>
                        <input type={'text'} className='form-control' name='opinion' placeholder='Opinion About Team' value={opinion} onChange={(e)=>onInputChange(e)}/>
                    </div>
                    <button type='submit' className='btn btn-outline-primary'>Add Fan</button>
                    <Link className='btn btn-outline-danger mx-2' to={"/fans"}>Cancel</Link>
                </form>
            </div> 
        </div>
    </div>
  )
}
