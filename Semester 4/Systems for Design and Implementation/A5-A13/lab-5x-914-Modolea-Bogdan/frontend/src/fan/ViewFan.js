import axios from 'axios';
import React, {useEffect, useState} from 'react'
import { Link, useParams } from 'react-router-dom'

export default function ViewFan() {
    // const toAddURL = 'lab-5x-914-Modolea-Bogdan/';
    const toAddURL = '';
    const serverLink = 'http://localhost:8080/fans';
    // const serverLink = 'http://esportsleaguemanager-env.eba-tbki6djt.eu-north-1.elasticbeanstalk.com/fans';

    const [fan, setFans] = useState({
        name: '',
        age: 0,
        nationality: '',
        occupation: '',
        placeOfBirth: ''
    });

    const {id} = useParams();

    useEffect(() => {
        loadFan();
    }, []);

    const loadFan = async()=>{
        const result = await axios.get(serverLink + `/${id}`);
        setFans(result.data);
    }

  return (
    <div className='container'>
        <div className='row'>
            <div className='col-md-6 offset-md-3 border rounded p-4 mt-2 shadow'>
                <h2 className='text-center m-4'>Fan Details</h2>
                <div className='card'>
                    <div className='card-header'>
                        <ul className='list-group list-group-flush'>
                            <li className='list-group-item'><b>Name: {fan.name}</b></li>
                            <li className='list-group-item'><b>Age: {fan.age}</b></li>
                            <li className='list-group-item'><b>Nationality: {fan.nationality}</b></li>
                            <li className='list-group-item'><b>Occupation: {fan.occupation}</b></li>
                            <li className='list-group-item'><b>Place Of Birth: {fan.placeOfBirth}</b></li>
                        </ul>
                    </div>
                </div>
                <Link className='btn btn-primary my-2' to={"/" + toAddURL + "fans"}>Back</Link>
            </div>
        </div>
    </div>
  )
}
