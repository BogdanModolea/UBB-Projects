import axios from 'axios';
// import { Dropdown } from 'bootstrap';
import DropdownButton from 'react-bootstrap/DropdownButton';
import Dropdown from 'react-bootstrap/Dropdown';
import React, { useEffect, useState } from 'react'
import { useNavigate, Link } from 'react-router-dom';

export default function AddTeam() {

    // const toAddURL = 'lab-5x-914-Modolea-Bogdan/';
    const toAddURL = '';
    const serverLink = 'http://localhost:8080/';
    // const serverLink = 'http://esportsleaguemanager-env.eba-tbki6djt.eu-north-1.elasticbeanstalk.com/';

    let navigate = useNavigate();
    
    const[myLeagues, setLeagues] = useState({});
    const[mapLeagues, setNewLeagues] = useState({});

    const [selectedItem, setSelectedItem] = useState(null);

    const [team, setTeam] = useState({
        name: '',
        top: '',
        jungle: '',
        mid: '',
        bot: '',
        support: '',
        leagueID: 0
    })

    const{name, top, jungle, mid, bot, support, leagueID} = team;

    const onInputChange=(e)=>{
        setTeam({...team, [e.target.name]: e.target.value});
    }

    useEffect(() => {
        axios.get(serverLink + `leagues`)
        .then(res => {
            const newDict = {};
            const newDict2= {};
            res.data.forEach(item => {
                newDict[item.abbreviation] = item.abbreviation;
                newDict2[item.abbreviation] = item;
            });
            setLeagues(newDict);
            setNewLeagues(newDict2);
    })
        .catch(err => console.log(err));
    }, []);

    const onSubmit=async(e)=>{
      e.preventDefault();
      team.leagueID = mapLeagues[selectedItem].lid;
      console.log(team);
      await axios.post(serverLink + "teams", team);
      navigate("/" + toAddURL +  "teams");
    };

    function renderDropdownItems(dict) {
        return Object.entries(dict).map(([key, value]) => (
          <Dropdown.Item key={key} eventKey={key}>
            {value}
          </Dropdown.Item>
        ));
      }

  return (
    <div className='container'>
        <div className='row'>
            <div className='col-md-6 offset-md-3 border rounded p-4 mt-2 shadow'>
                <h2 className='text-center m-4'>Add Team</h2>

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
                    <div className='mb-3'>
                        <DropdownButton
                            title={selectedItem || 'League Name'}
                            onSelect={key => setSelectedItem(key)}
                            value={leagueID}
                        >
                            {renderDropdownItems(myLeagues)}
                        </DropdownButton>
                    </div>
                    <button type='submit' className='btn btn-outline-primary'>Add Team</button>
                    <Link className='btn btn-outline-danger mx-2' to={"/" + toAddURL + "teams"}>Cancel</Link>
                </form>
            </div> 
        </div>
    </div>
  )
}
