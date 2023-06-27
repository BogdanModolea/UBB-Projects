import axios from 'axios';
import React, { useEffect, useState } from 'react'
import { useNavigate, Link, useParams } from 'react-router-dom';
import { ToastContainer, toast } from 'react-toastify';
import * as myConstClass from '../constants/constants';

export default function EditTeam() {

    let navigate = useNavigate();

    const {id} = useParams();

    const [league, setLeague] = useState({
        abbreviation: '',
        region: '',
        year: 0,
        bestPlayer: '',
        audience: 0
    })

    const{abbreviation, region, year, bestPlayer, audience} = league;

    const possibleRegions = ['EU', 'NA', 'PCS', 'JP', 'KR', 'CN', 'OCE'];

    const onInputChange=(e)=>{
        setLeague({...league, [e.target.name]: e.target.value});
    }

    useEffect(()=>{
        loadLeague();
    }, []);

    const onSubmit=async(e)=>{
        e.preventDefault();

        if(league.name === ''){
            toast.warn('Name is required!', {
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
        else if(league.region === ''){
            toast.warn('Region is required!', {
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
        else if(possibleRegions.includes(league.region) === false){
            toast.warn('Region is wrong!', {
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
        else if(league.year < 2010 || league.year > 2023){
            toast.warn('Year range is wrong!', {
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
        else if(league.year === 0){
            toast.warn('Year is required!', {
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
        else if(league.bestPlayer === ''){
            toast.warn('Best Player is required!', {
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
        else if(league.audience < 0){
            toast.warn('Audience can not be a negative number!', {
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
        else if(league.description === ''){
            toast.warn('Description is required!', {
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


            await axios.put(`${myConstClass.SERVER_LINK}/leagues/${id}`, league, {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });
            navigate("/leagues");
        }
        catch(err){
            toast.error(`You can't do that!`, {
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
    };

    const loadLeague = async()=>{
        const result = await axios.get(`${myConstClass.SERVER_LINK}/leagues/${id}`);
        setLeague(result.data);
    }

  return (
    <div className='container'>
        <div className='row'>
            <div className='col-md-6 offset-md-3 border rounded p-4 mt-2 shadow'>
                <h2 className='text-center m-4'>Edit League</h2>

                <form onSubmit={(e)=>onSubmit(e)}>
                    <div className='mb-3'>
                        <label htmlFor='abbreviation' className='form-label'>League Name</label>
                        <input type={'text'} className='form-control' name='abbreviation' placeholder='Enter League Name' value={abbreviation} onChange={(e)=>onInputChange(e)}/>
                    </div>
                    <div className='mb-3'>
                        <label htmlFor='region' className='form-label'>Region Name</label>
                        <input type={'text'} className='form-control' name='region' placeholder='Enter Region Name' value={region} onChange={(e)=>onInputChange(e)}/>
                    </div>
                    <div className='mb-3'>
                        <label htmlFor='year' className='form-label'>Active Since</label>
                        <input type={'number'} className='form-control' name='year' placeholder='Active Since' value={year} onChange={(e)=>onInputChange(e)}/>
                    </div>
                    <div className='mb-3'>
                        <label htmlFor='bestPlayer' className='form-label'>Best Player Name</label>
                        <input type={'text'} className='form-control' name='bestPlayer' placeholder='Enter Best Player Name' value={bestPlayer} onChange={(e)=>onInputChange(e)}/>
                    </div>
                    <div className='mb-3'>
                        <label htmlFor='audience' className='form-label'>Audience</label>
                        <input type={'number'} className='form-control' name='audience' placeholder='Enter Audience' value={audience} onChange={(e)=>onInputChange(e)}/>
                    </div>
                    <button type='submit' className='btn btn-outline-primary'>Update League</button>
                    <Link className='btn btn-outline-danger mx-2' to={"/leagues"}>Cancel</Link>
                    <ToastContainer />
                </form>
            </div> 
        </div>
    </div>
  )
}
