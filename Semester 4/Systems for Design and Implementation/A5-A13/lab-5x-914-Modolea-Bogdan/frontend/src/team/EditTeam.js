import axios from 'axios';
import React, { useEffect, useState } from 'react'
import { useNavigate, Link, useParams } from 'react-router-dom';

export default function EditTeam() {

    // const toAddURL = 'lab-5x-914-Modolea-Bogdan/';
    const toAddURL = '';
    const serverLink = 'http://localhost:8080/teams';
    // const serverLink = 'http://esportsleaguemanager-env.eba-tbki6djt.eu-north-1.elasticbeanstalk.com/teams';

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
      await axios.put(serverLink + `/${id}`, team);
      navigate("/" + toAddURL +  "teams");
    };

    const loadTeam = async()=>{
        const result = await axios.get(serverLink + `/${id}`);
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
                    <Link className='btn btn-outline-danger mx-2' to={"/" + toAddURL + "teams"}>Cancel</Link>
                </form>
            </div> 
        </div>
    </div>
  )
}
