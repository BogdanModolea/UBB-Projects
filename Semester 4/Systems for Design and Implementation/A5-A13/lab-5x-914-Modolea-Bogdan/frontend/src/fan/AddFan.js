import axios from 'axios';
import React, { useState } from 'react'
import { useNavigate, Link } from 'react-router-dom';

export default function AddFan() {

    // const toAddURL = 'lab-5x-914-Modolea-Bogdan/';
    const toAddURL = '';
    const serverLink = 'http://localhost:8080/fans';
    // const serverLink = 'http://esportsleaguemanager-env.eba-tbki6djt.eu-north-1.elasticbeanstalk.com/fans';

    let navigate = useNavigate();

    const [fan, setFan] = useState({
        name: '',
        age: 0,
        nationality: '',
        occupation: '',
        placeOfBirth: ''
    })

    const{name, age, nationality, occupation, placeOfBirth} = fan;

    const onInputChange=(e)=>{
        setFan({...fan, [e.target.name]: e.target.value});
    }

    const onSubmit=async(e)=>{
      e.preventDefault();
      await axios.post(serverLink, fan);
      navigate("/" + toAddURL +  "fans");
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
                    <Link className='btn btn-outline-danger mx-2' to={"/" + toAddURL + "fans"}>Cancel</Link>
                </form>
            </div> 
        </div>
    </div>
  )
}
