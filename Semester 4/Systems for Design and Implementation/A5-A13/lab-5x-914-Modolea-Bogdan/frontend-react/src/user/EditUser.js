import axios from 'axios';
import React, { useEffect, useState } from 'react'
import { useNavigate, Link, useParams } from 'react-router-dom';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import DropdownButton from 'react-bootstrap/DropdownButton';
import Dropdown from 'react-bootstrap/Dropdown';
import * as myConstClass from '../constants/constants';

export default function EditUser() {

    let navigate = useNavigate();

    const {username} = useParams();
    const[myRole, setRole] = useState({});
    const [selectedItem, setSelectedItem] = useState(null);

    const [rolepage, setRolePage] = useState({
        role: '',
        numberOfPages: 0
    });

    const{role, numberOfPages} = rolepage;

    const onInputChange=(e)=>{
        setRolePage({...rolepage, [e.target.name]: e.target.value});
    }

    useEffect(()=>{
        loadUser();
    }, []);

    const loadUser = async()=>{
        const result = await axios.get(`${myConstClass.SERVER_LINK}/user/${username}`);

        const newDict = {};
        newDict['ROLE_ADMIN'] = 'ADMIN';
        newDict['ROLE_MODERATOR'] = 'MODERATOR';
        newDict['ROLE_USER'] = 'USER';
        
        setSelectedItem(result.data.roles);
        setRole(newDict);

        rolepage.role = result.data.roles;
        rolepage.numberOfPages = result.data.recordsOnPage;
    }

    const onSubmit=async(e)=>{
        e.preventDefault();
        rolepage.role = selectedItem;
        console.log(rolepage);

        const token = JSON.parse(localStorage.getItem('login')).store;
        await axios.put(`${myConstClass.SERVER_LINK}/user/update/${username}`, rolepage, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        navigate("/users");
    }

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
                <h2 className='text-center m-4'>Edit User</h2>

                <form onSubmit={(e)=>onSubmit(e)}>
                    <DropdownButton
                        title={selectedItem || 'Role Name'}
                        onSelect={key => setSelectedItem(key)}
                        value={role}>
                            {renderDropdownItems(myRole)}
                    </DropdownButton>
                    <div className='mb-3'>
                        <label htmlFor='numberOfPages' className='form-label'>Number Of Pages</label>
                        <input type={'number'} className='form-control' name='numberOfPages' placeholder='Enter Number Of Pages' value={numberOfPages} onChange={(e)=>onInputChange(e)}/>
                    </div>
                    <button type='submit' className='btn btn-outline-primary'>Update User</button>
                    <Link className='btn btn-outline-danger mx-2' to={"/users"}>Cancel</Link>
                    <ToastContainer />
                </form>
            </div> 
        </div>
    </div>
    )
}
