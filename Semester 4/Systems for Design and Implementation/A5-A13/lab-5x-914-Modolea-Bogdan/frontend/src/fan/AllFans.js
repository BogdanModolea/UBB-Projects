import axios from 'axios';
import React, { useEffect, useState } from 'react'
import { Link, useParams, useNavigate } from 'react-router-dom'


export default function AllFans() {
  
    // const toAddURL = 'lab-5x-914-Modolea-Bogdan/';
    const toAddURL = '';
    const serverLink = 'http://localhost:8080/fans';
    // const serverLink = 'http://esportsleaguemanager-env.eba-tbki6djt.eu-north-1.elasticbeanstalk.com/fans';

    let navigate = useNavigate();

    const[fans, setFans] = useState([]);
    const {id} = useParams();

    useEffect(() => {
        loadFans();
    }, []);

    const loadFans=async()=>{
        const result = await axios.get(serverLink);
        setFans(result.data);
    }
  
    const deleteFan = async(id)=>{
        await axios.delete(serverLink + `/${id}`);
        loadFans();
    }

    const [fanAge, setFanAge] = useState(0);

    const {age} = fanAge;

    const onInputChange=(e)=>{
        setFanAge({...fanAge, [e.target.name]: e.target.value});
    }

    const onSubmit=async(e)=>{
        e.preventDefault();
        console.log(age);
        // navigate("/" + toAddURL +  "filterFans/" + age);
      };

    return (
    <div className='container'>
        <div className='py-4'>
            <div className='py-2'>
                <Link className='btn btn-outline-primary mx-1' to={"/" + toAddURL + "addFan"}>Add Fan</Link>
            </div>
            <div className='py-2'>
                <form onSubmit={(e)=>onSubmit(e)}>
                    <div class="d-flex justify-content-center">
                        <div class="input-group w-auto">
                            <input type={'number'} className='form-control' name='age' placeholder='Enter Fan Age' value={age} onChange={(e)=>onInputChange(e)}/>
                            {/* <button type='submit' className='btn btn-outline-primary'>Filter Fans</button> */}
                            <Link type='submit' className='btn btn-primary mx-1' to={`/${toAddURL}filterFans/${age}`}>Filter Fans</Link>
                        </div>
                    </div>
                </form>
            </div>
            <table className="table border shadow">
                <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Name</th>
                        <th scope="col">Age</th>
                        <th scope="col">Nationality</th>
                        <th scope="col">Occupation</th>
                        <th scope="col">Place Of Birth</th>
                        <th scope="col">Action</th>
                    </tr>
                </thead>
                <tbody>

                    {
                        fans.map((fan, index) => (
                            <tr>
                                <th scope="row" key={index}>{index + 1}</th>
                                <td>{fan.name}</td>
                                <td>{fan.age}</td>
                                <td>{fan.nationality}</td>
                                <td>{fan.occupation}</td>
                                <td>{fan.placeOfBirth}</td>
                                <td>
                                    <Link className='btn btn-primary mx-1' to={`/${toAddURL}viewFan/${fan.fid}`}>View</Link>
                                    <Link className='btn btn-outline-primary mx-1' to={`/${toAddURL}updateFan/${fan.fid}`} >Edit</Link>
                                    <button className='btn btn-danger mx-1' onClick={()=>deleteFan(fan.fid)}>Delete</button>
                                </td>
                            </tr>
                        ))
                    }

                </tbody>
            </table>
        </div>
    </div>
  )
}
