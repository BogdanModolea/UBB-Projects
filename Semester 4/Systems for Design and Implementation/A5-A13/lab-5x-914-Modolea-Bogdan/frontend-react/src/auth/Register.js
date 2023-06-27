import React, { useState } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import * as myConstClass from '../constants/constants';

class Register extends React.Component {
    constructor() {
        super();
        this.state = {
            name: '',
            email: '',
            password: '',
            age: 0,
            location: '',
            verificationCode: '',
            confirmationCodeSentAt: null,
            register: null
        }
    }

    verifyCode = '';

    componentDidMount() {
        this.storeCollector();
    }

    storeCollector() {
        let register = JSON.parse(localStorage.getItem('register'));
        if (register) {
            this.setState({ register: true });
        }
    }

    isEmail(str) {
        const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailPattern.test(str);
    };

    async register() {
        const data = axios.get(`${myConstClass.SERVER_LINK}/user/${this.state.name}`);

        if(this.state.name === '') {
            toast.warn('The name must not be left empty', {
                position: "top-right",
                autoClose: 3000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                theme: "light",
            });
            return;
        }
        else if(data.name) {
            toast.warn('The name must be unique', {
                position: "top-right",
                autoClose: 3000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                theme: "light",
            });
            return;
        }
        else if(this.state.email === '') {
            toast.warn('The email must not be left empty!', {
                position: "top-right",
                autoClose: 3000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                theme: "light",
            });
            return;
        }
        else if(!this.isEmail(this.state.email)) {
            toast.warn('The email must be a valid email!', {
                position: "top-right",
                autoClose: 3000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                theme: "light",
            });
            return;
        }
        else if(this.state.password === '') {
            toast.warn('The password must not be left empty!', {
                position: "top-right",
                autoClose: 3000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                theme: "light",
            });
            return;
        }
        else if(this.state.age === 0) {
            toast.warn('The age must not be left empty!', {
                position: "top-right",
                autoClose: 3000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                theme: "light",
            });
            return;
        }
        else if(isNaN(Number(this.state.age))){
            toast.warn('The age must be a number!', {
                position: "top-right",
                autoClose: 3000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                theme: "light",
            });
            return;
        }
        else if(this.state.location === '') {
            toast.warn('The location must not be left empty!', {
                position: "top-right",
                autoClose: 3000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                theme: "light",
            });
            return;
        }

        try{
            const result = await axios.post(`${myConstClass.SERVER_LINK}/user/register`, this.state);
            this.verifyCode = result.data.code;
            this.state.verificationCode = result.data.code;
            this.state.confirmationCodeSentAt = result.data.registerAt;

            localStorage.setItem('register', JSON.stringify({
                state: this.state,
                register: false
            }))

            this.storeCollector();
        }
        catch(err) {
            toast.warn('Something went wrong!', {
                position: "top-right",
                autoClose: 3000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                theme: "light",
            });
            return;
        }
    }

    async verify() {
        this.state = JSON.parse(localStorage.getItem('register')).state;
        console.log(this.state);
        console.log(this.state.verificationCode);
        const result = await axios.put(`${myConstClass.SERVER_LINK}/user/register/verify/${this.state.verificationCode}`, this.state);

        localStorage.setItem('register', JSON.stringify({
            register: true
        }))

        this.storeCollector();
        window.location.href = '/';
    }

    cancel() {
        localStorage.clear();
        this.setState({ register: false });
    }

    render() {
        return (
            <div>
                { !this.state.register ?
                    <div className='col-md-6 offset-md-3 border rounded p-4 mt-2 shadow'>
                        <h2 className='text-center m-4'>Register</h2>
                        <div className='mb-3'>
                            <label className='form-label'>Username</label>
                            <input className='form-control' placeholder='Enter Username' type='text' onChange={(event) => this.setState({ name: event.target.value })} />
                        </div>
                        <div className='mb-3'>
                            <label className='form-label'>Email</label>
                            <input className='form-control' placeholder='Enter Email' type='text' onChange={(event) => this.setState({ email: event.target.value })} />
                        </div>
                        <div className='mb-3'>
                            <label className='form-label'>Password</label>
                            <input className='form-control' placeholder='Enter Password' type='password' onChange={(event) => this.setState({ password: event.target.value })} />
                        </div>
                        <div className='mb-3'>
                            <label className='form-label'>Age</label>
                            <input className='form-control' placeholder='Enter Age' type='text' onChange={(event) => this.setState({ age: event.target.value })} />
                        </div>
                        <div className='mb-3'>
                            <label className='form-label'>Location</label>
                            <input className='form-control' placeholder='Enter Location' type='text' onChange={(event) => this.setState({ location: event.target.value })} />
                        </div>
                        <button className='btn btn-outline-primary' onClick={() => this.register()}>Register</button>
                        <Link className='btn btn-outline-danger mx-2' to={"/"}>Back</Link>
                        <ToastContainer />
                    </div>
                :
                <div>
                    <h1>You have 10 minutes to verify account!</h1>
                    <button className='btn btn-primary my-2' onClick={() => this.verify()}>Verify</button>
                </div>
            }
            </div>
        );
    }
}

export default Register;