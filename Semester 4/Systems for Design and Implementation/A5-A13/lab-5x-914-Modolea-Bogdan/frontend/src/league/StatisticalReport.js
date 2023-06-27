import axios from 'axios';
import React, { useEffect, useState } from 'react'
import { Link, useParams } from 'react-router-dom'

export default function StatisticalReport() {
  
    // const toAddURL = 'lab-5x-914-Modolea-Bogdan/';
    const toAddURL = '';
    const serverLink = 'http://localhost:8080/leagues';
    // const serverLink = 'http://esportsleaguemanager-env.eba-tbki6djt.eu-north-1.elasticbeanstalk.com/leagues';

    const[leagues, setLeagues] = useState([]);

    useEffect(() => {
        loadLeagues();
    }, []);

    const loadLeagues=async()=>{
        const result = await axios.get(serverLink + "/number-of-nations-that-supports-league");
        setLeagues(result.data);
    }

    return (
    <div className='container'>
        <div className='py-4'>
            <div className='mb-2'>
            <Link className='btn btn-outline-danger mx-1' to={"/" + toAddURL + "leagues"}>Back</Link>
            </div>
            <table className="table border shadow">
                <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">League Name</th>
                        <th scope="col">Region</th>
                        <th scope="col">Number of Nationalities that supports it</th>
                    </tr>
                </thead>
                <tbody>

                    {
                        leagues.map((league, index) => (
                            <tr>
                                <th scope="row" key={index}>{index + 1}</th>
                                <td>{league.leagueName}</td>
                                <td>{league.region}</td>
                                <td>{league.counter}</td>
                            </tr>
                        ))
                    }
                </tbody>
            </table>
        </div>
    </div>
  )
}
