import axios from 'axios';
import React, { useEffect, useState } from 'react'
import { Link, useParams } from 'react-router-dom'
import * as myConstClass from '../constants/constants';
import Loading from '../constants/Loading';
import { Tooltip } from 'react-tooltip';
import { ToastContainer, toast } from 'react-toastify';

export default function AllLeagues() {

    const[leagues, setLeagues] = useState([]);
    const[loading, setLoading] = useState(true);

    const[currentPage, setCurrentPage] = useState(1);
    const[npage, setNPages] = useState(0);
    const[numbers1, setNumbers1] = useState([0, 1, 2, 3].slice(1));
    const[numbers2, setNumbers2] = useState([]);
    const[recordsPerPage, setRecordsPerPage] = useState(100);

    const {id} = useParams();

    useEffect(() => {
        loadPages();
    }, []);

    const loadLeaguesWithPage=async(page, recordsPage)=>{
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
        const result = await axios.get(`${myConstClass.SERVER_LINK}/leagues/stats/pagination/${page - 1}/${recordsPage}`);
        setLeagues(result.data.content);
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
            loadLeaguesWithPage(currentPage, pages.data.recordsOnPage);
        }
        else {
            setRecordsPerPage(100);
            loadLeaguesWithPage(currentPage, 100);
        }
    }   
  
    const deleteLeague = async(id)=>{
        try{
            const token = JSON.parse(localStorage.getItem('login')).store;
            await axios.delete(`${myConstClass.SERVER_LINK}/leagues/${id}`, {
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


    return (
        <div>
            {
                !loading ?
                    <div className='container'>
                        <div className='py-4'>
                            <div className='mb-2'>
                                <Link className='btn btn-outline-primary mx-1' to={"/addLeague"}>Add League</Link>
                                <Link className='btn btn-outline-primary mx-1' to={"/statisticalReport"} data-tip data-for='delete-btn' data-tooltip-id="my-tooltip" data-tooltip-content="Get data about the number of nationalities that support an Esport League">Statistics</Link>
                                <Tooltip id="my-tooltip" />
                            </div>
                            <table className="table border shadow">
                                <thead>
                                    <tr>
                                        <th scope="col">#</th>
                                        <th scope="col">League Name</th>
                                        <th scope="col">Region</th>
                                        <th scope="col">Active since</th>
                                        <th scope="col">Best Player</th>
                                        <th scope="col">Audience</th>
                                        <th scope="col">Description</th>
                                        <th scope="col">Number of Teams</th>
                                        <th scope="col">Added by</th>
                                        <th scope="col"></th>
                                        <th scope="col">Action</th>
                                        <th scope="col"></th>
                                        <th scope="col"></th>
                                    </tr>
                                </thead>
                                <tbody>

                                    {
                                        leagues.map((league, index) => (
                                            <tr>
                                                <th scope="row" key={index}>{index + 1}</th>
                                                <td>{league.abbreviation}</td>
                                                <td>{league.region}</td>
                                                <td>{league.year}</td>
                                                <td>{league.bestPlayer}</td>
                                                <td>{league.audience}</td>
                                                <td>{league.description}</td>
                                                <td>{league.counter}</td>
                                                <td><Link to={`/user/${league.username}`}>{league.username}</Link></td>
                                                <td>
                                                    <Link className='btn btn-primary mx-1' to={`/viewLeague/${league.lid}`}>View</Link>
                                                </td>
                                                <td>
                                                    <Link className='btn btn-outline-primary mx-1' to={`/updateLeague/${league.lid}`} >Edit</Link>
                                                </td>
                                                <td>
                                                    <button className='btn btn-danger mx-1' onClick={()=>deleteLeague(league.lid)}>Delete</button>
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
    console.log(currentPage);
    if(currentPage > 1){
        setCurrentPage(currentPage - 1);
        loadLeaguesWithPage(currentPage - 1, recordsPerPage);
    }
  }

  function changeCPage(id) { 
    if(id !== '...'){
        setCurrentPage(id);
        loadLeaguesWithPage(id, recordsPerPage);
    }
  }

  function nextPage() { 
    console.log(currentPage);
    if(currentPage < npage) {
        setCurrentPage(currentPage + 1);
        loadLeaguesWithPage(currentPage + 1, recordsPerPage);
    }
  }   
}
