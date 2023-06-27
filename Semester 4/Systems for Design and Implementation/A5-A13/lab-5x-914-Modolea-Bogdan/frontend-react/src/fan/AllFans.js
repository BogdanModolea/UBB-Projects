import axios from 'axios';
import React, { useEffect, useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import * as myConstClass from '../constants/constants';
import Loading from '../constants/Loading';
import { Tooltip } from 'react-tooltip';

export default function AllFans() {
    let navigate = useNavigate();

    const[fans, setFans] = useState([]);
    const[loading, setLoading] = useState(true);

    const[currentPage, setCurrentPage] = useState(1);
    const[npage, setNPages] = useState(0);
    const[numbers1, setNumbers1] = useState([0, 1, 2, 3].slice(1));
    const[numbers2, setNumbers2] = useState([]);
    const[recordsPerPage, setRecordsPerPage] = useState(100);

    useEffect(() => {
        loadPages();
    }, []);

    const loadFansWithPage=async(page, recordsPage)=>{
        if(page === 1){
            setNumbers1([0, 1, 2, 3].slice(1));
            if(npage !== 0)
                setNumbers2([npage - 3, npage - 2, npage - 1, npage].slice(1));
        }else if(page === 2){
            setNumbers1([0, 1, 2, 3, 4].slice(1));
            if(npage !== 0)
                setNumbers2([npage - 3, npage - 2, npage - 1, npage].slice(1));
        }else if(page === 3){
            setNumbers1([0, 1, 2, 3, 4, 5].slice(1));
            if(npage !== 0)
                setNumbers2([npage - 3, npage - 2, npage - 1, npage].slice(1));
        }else if(page === 4){
            setNumbers1([0, 1, 2, 3, 4, 5, 6].slice(1));
            if(npage !== 0)
                setNumbers2([npage - 3, npage - 2, npage - 1, npage].slice(1));
        }else if(page === npage){
            setNumbers1([0, 1, 2, 3].slice(1));
            if(npage !== 0)
                setNumbers2([npage - 2, npage - 1, npage].slice(1));
        }else if(page === npage - 1){
            setNumbers1([0, 1, 2, 3].slice(1));
            if(npage !== 0)
                setNumbers2([page - 3, page - 2, page - 1, page, page + 1].slice(1));
        }else if(page === npage - 2){
            setNumbers1([0, 1, 2, 3].slice(1));
            if(npage !== 0)
                setNumbers2([page - 3, page - 2, page - 1, page, page + 1, page + 2].slice(1));
        }else if(page === npage - 3){
            setNumbers1([0, 1, 2, 3].slice(1));
            if(npage !== 0)
                setNumbers2([page - 3, page - 2, page - 1, page, page + 1, page + 2].slice(1));
        }else if(page == npage - 4){
            setNumbers1([0, 1, 2, 3].slice(1));
            if(npage !== 0)
                setNumbers2([page - 3, page - 2, page - 1, page, page + 1, page + 2, page + 3, page + 4].slice(1));
        }
        else{
            setNumbers1([0, 1, 2, 3, '...', page - 2, page - 1, page, page + 1, page + 2].slice(1));
            if(npage !== 0)
                setNumbers2([npage - 3, npage - 2, npage - 1, npage].slice(1));
        }
        setLoading(true);
        const result = await axios.get(`${myConstClass.SERVER_LINK}/fans/stats/pagination/${page - 1}/${recordsPage}`);
        setFans(result.data.content);
        setNPages(result.data.totalPages);
        setLoading(false);
        const lastpage = result.data.totalPages;
        setNumbers2([lastpage - 3, lastpage - 2, lastpage - 1, lastpage].slice(1));
    }

    const loadPages=async()=>{
        const token = JSON.parse(localStorage.getItem('login'));
        if(token !== null) {
            const user = await axios.get(`${myConstClass.SERVER_LINK}/user/getUsername/${token.store}`);
            const pages = await axios.get(`${myConstClass.SERVER_LINK}/user/${user.data}`);
            setRecordsPerPage(pages.data.recordsOnPage);
            loadFansWithPage(currentPage, pages.data.recordsOnPage);
        }
        else{
            setRecordsPerPage(100);
            loadFansWithPage(currentPage, 100);
        }
    }   
  
    const deleteFan = async(id)=>{
        try{
            const token = JSON.parse(localStorage.getItem('login'));
            await axios.delete(`${myConstClass.SERVER_LINK}/fans` + `/${id}`, {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });
            loadPages();
        }
        catch(err){
            toast.warn(`You can't do that!`, {
                position: "top-right",
                autoClose: 3000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                theme: "light",
            });
        }
    }

    const [fanAge, setFanAge] = useState(0);
    const {age} = fanAge;

    const onInputChange=(e)=>{
        setFanAge({...fanAge, [e.target.name]: e.target.value});
    }

    const onSubmit=async(e)=>{
        e.preventDefault();
      };

    const displayPredict = async() => {
        navigate("/predictFans");
    }



    return (
        <div>
            {
                !loading ?
                    <div className='container'>
                        <div className='py-4'>
                            <div className='py-2'>
                                <Link className='btn btn-outline-secondary mx-1' to={"/nationalities"} data-tooltip-id="my-tooltip" data-tooltip-content="Get the number of fans by nationalities">Nationalities</Link>
                                <Link className='btn btn-outline-primary mx-1' to={"/addFan"}>Add Fan</Link>
                                <button className='btn btn-outline-secondary mx-1' onClick={()=>displayPredict()} data-tooltip-id="my-tooltip" data-tooltip-content="Predict the number of fans for next 3 years">Future Fans</button>
                                <Tooltip id="my-tooltip" />
                            </div>
                            <div className='py-2'>
                                <form onSubmit={(e)=>onSubmit(e)}>
                                    <div class="d-flex justify-content-center">
                                        <div class="input-group w-auto">
                                            <input type={'number'} className='form-control' name='age' placeholder='Enter Fan Age' value={age} onChange={(e)=>onInputChange(e)}/>
                                            <Link type='submit' className='btn btn-primary mx-1' to={`/filterFans/${age}`}>Filter Fans by Age</Link>
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
                                        <th scope="col">Number of Teams</th>
                                        <th scope="col">Added by</th>
                                        <th scope='col'></th>
                                        <th scope="col">Action</th>
                                        <th scope='col'></th>
                                        <th scope='col'></th>
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
                                                <td>{fan.counter}</td>
                                                <td><Link to={`/user/${fan.username}`}>{fan.username}</Link></td>
                                                <td>
                                                    <Link className='btn btn-primary mx-1' to={`/viewFan/${fan.fid}`}>View</Link>
                                                </td>
                                                <td>
                                                    <Link className='btn btn-outline-primary mx-1' to={`/updateFan/${fan.fid}`} >Edit</Link>
                                                </td>
                                                <td>
                                                    <button className='btn btn-danger mx-1' onClick={()=>deleteFan(fan.fid)}>Delete</button>
                                                </td>
                                                <td>
                                                    <Link className='btn btn-outline-primary mx-1' to={`/addTeamToFan/${fan.fid}`} >Add Team</Link>
                                                </td>
                                            </tr>
                                        ))
                                    }
                                </tbody>
                            </table>
                            <nav>
                                <ul className='pagination'>
                                    <li className='page-item'>
                                        <a href='#' className='page-link'
                                        onClick={prePage}
                                        >Prev
                                        </a>
                                    </li>
                                    {
                                        numbers1.map((n, i) => (
                                            <li className={`page-item ${currentPage === n && n != '...' ? 'active' : ''}`} key={i}>
                                                <a href='#' className='page-link'
                                                onClick={() => changeCPage(n)}>{n}</a>
                                            </li>
                                        ))
                                    }

                                    <li className='page-item'>
                                        <a href='#' className='page-link'>...</a>
                                    </li>
                                    
                                    {   
                                        numbers2.map((n, i) => (
                                            <li className={`page-item ${currentPage === n ? 'active' : ''}`} key={i}>
                                                <a href='#' className='page-link'
                                                onClick={() => changeCPage(n)}>{n}</a>
                                            </li>
                                        ))
                                    }
                                    <li className='page-item'>
                                        <a href='#' className='page-link'
                                        onClick={nextPage}
                                        >Next
                                        </a>
                                    </li>
                                </ul>
                            </nav>
                        </div>
                        <ToastContainer />
                    </div>
                    :
                    <Loading />
                }
            </div>
  )

  function prePage(){
    if(currentPage > 1){
        setCurrentPage(currentPage - 1);
        loadFansWithPage(currentPage - 1, recordsPerPage);
    }
  }

  function changeCPage(id) { 
    setCurrentPage(id);
    loadFansWithPage(id, recordsPerPage);
  }

  function nextPage() { 
    if(currentPage < npage) {
        setCurrentPage(currentPage + 1);
        loadFansWithPage(currentPage + 1, recordsPerPage);
    }
  }   
}
