import axios from 'axios';
import React, { useEffect, useState } from 'react'
import { Link, useParams } from 'react-router-dom'

export default function AllTeams() {
  
    const toAddURL = '';
    // const serverLink = 'http://localhost:8080';
    const serverLink = 'http://ec2-16-16-4-156.eu-north-1.compute.amazonaws.com:8080';
    // const serverLink = '13.49.218.254';
    
    const[teams, setTeams] = useState([]);

    const {id} = useParams();

    useEffect(() => {
        loadTeams();
    }, []);

    const loadTeams=async()=>{
        console.log(serverLink);
        const result = await axios.get(`${serverLink} + /teams`);
        setTeams(result.data);
    }
  
    const deleteTeam = async(id)=>{
        await axios.delete(serverLink + `/teams/${id}`);
        loadTeams();
    }


    return (
    <div className='container'>
        <div className='py-4'>
            <div className='mb-2'>
                <Link className='btn btn-outline-primary mx-1' to={"/" + toAddURL + "addTeam"}>Add Team</Link>
            </div>
            <table className="table border shadow">
                <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Team Name</th>
                        <th scope="col">Toplaner</th>
                        <th scope="col">Jungler</th>
                        <th scope="col">Midlaner</th>
                        <th scope="col">Botlaner</th>
                        <th scope="col">Support</th>
                        <th scope="col">Action</th>
                    </tr>
                </thead>
                <tbody>

                    {
                        teams.map((team, index) => (
                            <tr>
                                <th scope="row" key={index}>{index + 1}</th>
                                <td>{team.name}</td>
                                <td>{team.top}</td>
                                <td>{team.jungle}</td>
                                <td>{team.mid}</td>
                                <td>{team.bot}</td>
                                <td>{team.support}</td>
                                <td>
                                    <Link className='btn btn-primary mx-1' to={`/${toAddURL}viewTeam/${team.id}`}>View</Link>
                                    <Link className='btn btn-outline-primary mx-1' to={`/${toAddURL}updateTeam/${team.id}`} >Edit</Link>
                                    <button className='btn btn-danger mx-1' onClick={()=>deleteTeam(team.id)}>Delete</button>
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
