import { wait } from '@testing-library/user-event/dist/utils';
import axios from 'axios';
import React, {useEffect, useState} from 'react'
import { Link, useParams } from 'react-router-dom'

var teams = [];

export default function ViewTeam() {
    // const toAddURL = 'lab-5x-914-Modolea-Bogdan/';
    const toAddURL = '';
    const serverLink = 'http://localhost:8080/leagues';
    // const serverLink = 'http://esportsleaguemanager-env.eba-tbki6djt.eu-north-1.elasticbeanstalk.com/leagues';

    const [league, setLeague] = useState({
        abbreviation: '',
        region: '',
        year: 0,
        bestPlayer: '',
        audience: 0
    })

    const {id} = useParams();

    useEffect(() => {
        loadLeague();
    }, []);

    const loadLeague = async()=>{
        const result = await axios.get(serverLink + `/${id}`);
        setLeague(result.data);
        teams = result.data.teams;
    }

  return (
    <div className='container'>
        <div className='row'>
            <div className='col-md-6 offset-md-3 border rounded p-4 mt-2 shadow'>
                <h2 className='text-center m-4'>League Details</h2>
                <div className='card'>
                    <div className='card-header'>
                        <ul className='list-group list-group-flush'>
                            <li className='list-group-item'><b>League Name: {league.abbreviation}</b></li>
                            <li className='list-group-item'><b>Region: {league.region}</b></li>
                            <li className='list-group-item'><b>Active since: {league.year}</b></li>
                            <li className='list-group-item'><b>Best Player: {league.bestPlayer}</b></li>
                            <li className='list-group-item'><b>Audience: {league.audience}</b></li>
                        </ul>
                    </div>
                </div>
                <Link className='btn btn-primary my-2' to={"/" + toAddURL + "leagues"}>Back</Link>
            </div>
        </div>
    </div>
  )
}
