import axios from 'axios';
import React, {useEffect, useState} from 'react'
import { Link, useParams } from 'react-router-dom'

export default function ViewTeam() {
    // const toAddURL = 'lab-5x-914-Modolea-Bogdan/';
    const toAddURL = '';
    // const serverLink = 'http://localhost:8080/teams';
    const serverLink = 'https://leaguemanagersdi-env.eba-pnmmng2r.eu-north-1.elasticbeanstalk.com/teams';

    const[loading, setLoading] = useState(false);

    const [team, setTeam] = useState({
        name: '',
        top: '',
        jungle: '',
        mid: '',
        bot: '',
        support: '',
        league: {}
    });

    const {id} = useParams();

    useEffect(() => {
        setLoading(true);
        fetch(serverLink)
        .then(res => res.json())
        .then(data => {
            setTeam(data);
            setLoading(false);
        });
        
        //loadTeam();
    }, []);

    const loadTeam = async()=>{
        const result = await axios.get(serverLink + `/${id}`);
        setTeam(result.data);
    }

  return (
    <div className='container'>
        <div className='row'>
            <div className='col-md-6 offset-md-3 border rounded p-4 mt-2 shadow'>
                <h2 className='text-center m-4'>Team Details</h2>
                <div className='card'>
                    <div className='card-header'>
                        <ul className='list-group list-group-flush'>
                            <li className='list-group-item'><b>Team Name: {team.name}</b></li>
                            <li className='list-group-item'><b>Toplaner: {team.top}</b></li>
                            <li className='list-group-item'><b>Jungler: {team.jungle}</b></li>
                            <li className='list-group-item'><b>Midlaner: {team.mid}</b></li>
                            <li className='list-group-item'><b>Botlaner: {team.bot}</b></li>
                            <li className='list-group-item'><b>Support: {team.support}</b></li>
                            <li className='list-group-item'><b>League: {team.league.abbreviation}</b></li>
                        </ul>
                    </div>
                </div>
                <Link className='btn btn-primary my-2' to={"/" + toAddURL + "teams"}>Back</Link>
            </div>
        </div>
    </div>
  )
}
