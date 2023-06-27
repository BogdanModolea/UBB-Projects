import axios from 'axios';
import React, { useEffect, useState } from 'react'
import { useNavigate, Link, useParams } from 'react-router-dom';
import { ToastContainer, toast } from 'react-toastify';
import * as myConstClass from '../constants/constants';

export default function EditFan() {

    let navigate = useNavigate();

    const {id} = useParams()

    const [fan, setFans] = useState({
        name: '',
        age: 0,
        nationality: '',
        occupation: '',
        placeOfBirth: ''
    });

    const{name, age, nationality, occupation, placeOfBirth} = fan;

    const onInputChange=(e)=>{
        setFans({...fan, [e.target.name]: e.target.value});
    }

    useEffect(()=>{
        loadFan();
    }, []);

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
        try{
            const token = JSON.parse(localStorage.getItem('login')).store;

            await axios.put(`${myConstClass.SERVER_LINK}/fans/${id}`, fan, {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });
            navigate("/fans");
        }
        catch(err){
            toast.error('Error updating fan!', {
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

    const loadFan = async()=>{
        const result = await axios.get(`${myConstClass.SERVER_LINK}/fans/${id}`);
        setFans(result.data);
    }

  return (
    <div className='container'>
        <div className='row'>
            <div className='col-md-6 offset-md-3 border rounded p-4 mt-2 shadow'>
                <h2 className='text-center m-4'>Edit Fan</h2>

                <form onSubmit={(e)=>onSubmit(e)}>
                    <div className='mb-3'>
                        <label htmlFor='name' className='form-label'>Fan Name</label>
                        <input type={'text'} className='form-control' name='name' placeholder='Enter Team Name' value={name} onChange={(e)=>onInputChange(e)}/>
                    </div>
                    <div className='mb-3'>
                        <label htmlFor='age' className='form-label'>Fan Age</label>
                        <input type={'text'} className='form-control' name='age' placeholder='Enter Fan Age' value={age} onChange={(e)=>onInputChange(e)}/>
                    </div>
                    <div className='mb-3'>
                        <label htmlFor='nationality' className='form-label'>Fan Nationality</label>
                        <input type={'text'} className='form-control' name='nationality' placeholder='Enter Fan Nationality' value={nationality} onChange={(e)=>onInputChange(e)}/>
                    </div>
                    <div className='mb-3'>
                        <label htmlFor='occupation' className='form-label'>Fan Occupation</label>
                        <input type={'text'} className='form-control' name='occupation' placeholder='Enter Fan Occupation' value={occupation} onChange={(e)=>onInputChange(e)}/>
                    </div>
                    <div className='mb-3'>
                        <label htmlFor='placeOfBirth' className='form-label'>Place Of Birth</label>
                        <input type={'text'} className='form-control' name='placeOfBirth' placeholder='Enter Place Of Birth' value={placeOfBirth} onChange={(e)=>onInputChange(e)}/>
                    </div>
                    <button type='submit' className='btn btn-outline-primary'>Update Fan</button>
                    <Link className='btn btn-outline-danger mx-2' to={"/fans"}>Cancel</Link>
                    <ToastContainer />
                </form>
            </div> 
        </div>
    </div>
  )
}
