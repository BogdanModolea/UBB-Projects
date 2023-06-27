import axios from 'axios';
import React, { useEffect, useState } from 'react'
import { Link, useParams, useNavigate } from 'react-router-dom'


export default function FilterFans() {
  
    // const toAddURL = 'lab-5x-914-Modolea-Bogdan/';
    const toAddURL = '';
    const serverLink = 'http://localhost:8080/fans';
    // const serverLink = 'http://esportsleaguemanager-env.eba-tbki6djt.eu-north-1.elasticbeanstalk.com/fans';

    let navigate = useNavigate();

    const[fans, setFans] = useState([]);
    const {age} = useParams();

    useEffect(() => {
        loadFans();
    }, []);

    const loadFans=async()=>{
        const result = await axios.get(serverLink + `/filter/${age}`);
        setFans(result.data);
    }

    return (
    <div className='container'>
        <div className='py-4'>
            <div className='py-2'>
                <Link className='btn btn-outline-danger mx-1' to={"/" + toAddURL + "fans"}>Back</Link>
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
                            </tr>
                        ))
                    }

                </tbody>
            </table>
        </div>
    </div>
  )
}
