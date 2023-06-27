import axios from 'axios';
import React, { useEffect, useState } from 'react'
import { useNavigate, Link, useParams } from 'react-router-dom';

export default function EditFan() {

    // const toAddURL = 'lab-5x-914-Modolea-Bogdan/';
    const toAddURL = '';
    const serverLink = 'http://localhost:8080/fans';
    // const serverLink = 'http://esportsleaguemanager-env.eba-tbki6djt.eu-north-1.elasticbeanstalk.com/fans';

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
      await axios.put(serverLink + `/${id}`, fan);
      navigate("/" + toAddURL +  "fans");
    };

    const loadFan = async()=>{
        const result = await axios.get(serverLink + `/${id}`);
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
                    <Link className='btn btn-outline-danger mx-2' to={"/" + toAddURL + "fans"}>Cancel</Link>
                </form>
            </div> 
        </div>
    </div>
  )
}
