import axios from 'axios';
import React, { useState } from 'react'
import { useNavigate, Link } from 'react-router-dom';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import * as myConstClass from '../constants/constants';


export default function AddFan() {
    let navigate = useNavigate();

    const [fan, setFan] = useState({
        name: '',
        age: 0,
        nationality: '',
        occupation: '',
        placeOfBirth: '',
        username: ''
    })

    const{name, age, nationality, occupation, placeOfBirth, username} = fan;

    const onInputChange=(e)=>{
        setFan({...fan, [e.target.name]: e.target.value});
    }

    const onSubmit=async(e)=>{
        e.preventDefault();

        if(fan.name === '') { 
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
        else if(fan.age === 0) {
            toast.warn('Age cannot be 0!', {
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
        else if(fan.nationality === '') {
            toast.warn('The fan has to have a nationality!', {
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
        else if(fan.occupation === '') {
            toast.warn('The fan has to have an occupation!', {
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
        else if(fan.placeOfBirth === '') {
            toast.warn('The fan has to be born somewhere!', {
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

        const token = JSON.parse(localStorage.getItem('login')).store;


        try{
            const currentUsername = await axios.get(`${myConstClass.SERVER_LINK}/user/getUsername/${token}`);
            fan.username = currentUsername.data;
            await axios.post(`${myConstClass.SERVER_LINK}/fans`, fan);
            navigate("/fans");
        }
        catch(err){
            toast.warn('Something went wrong!', {
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
                        <label htmlFor='name' className='form-label'>Name</label>
                        <input type={'text'} className='form-control' name='name' placeholder='Enter Fan Name' value={name} onChange={(e)=>onInputChange(e)}/>
                    </div>
                    <div className='mb-3'>
                        <label htmlFor='age' className='form-label'>Age</label>
                        <input type={'number'} className='form-control' name='age' placeholder='Enter Age' value={age} onChange={(e)=>onInputChange(e)}/>
                    </div>
                    <div className='mb-3'>
                        <label htmlFor='nationality' className='form-label'>Nationality</label>
                        <input type={'text'} className='form-control' name='nationality' placeholder='Enter Nationality' value={nationality} onChange={(e)=>onInputChange(e)}/>
                    </div>
                    <div className='mb-3'>
                        <label htmlFor='occupation' className='form-label'>Occupation</label>
                        <input type={'text'} className='form-control' name='occupation' placeholder='Enter Occupation' value={occupation} onChange={(e)=>onInputChange(e)}/>
                    </div>
                    <div className='mb-3'>
                        <label htmlFor='placeOfBirth' className='form-label'>Place Of Birth</label>
                        <input type={'text'} className='form-control' name='placeOfBirth' placeholder='Enter Place Of Birth' value={placeOfBirth} onChange={(e)=>onInputChange(e)}/>
                    </div>
                    <button type='submit' className='btn btn-outline-primary'>Add Fan</button>
                    <Link className='btn btn-outline-danger mx-2' to={"/fans"}>Cancel</Link>
                    <ToastContainer />
                </form>
            </div> 
        </div>
    </div>
  )
}
