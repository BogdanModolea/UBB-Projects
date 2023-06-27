import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import * as myConstClass from '../constants/constants';
import Loading from '../constants/Loading';

export default function AllTeams() {
    
    const[teams, setTeams] = useState([]);
    const[loading, setLoading] = useState(true);

    const[currentPage, setCurrentPage] = useState(1);
    const[npage, setNPages] = useState();
    const[numbers1, setNumbers1] = useState([0, 1, 2, 3].slice(1));
    const[numbers2, setNumbers2] = useState([]);
    const[recordsPerPage, setRecordsPerPage] = useState(100);


    const {id} = useParams();

    useEffect(() => {
        loadPages();
    }, []);

    const loadTeamsWithPage=async(page, recordsPage)=>{
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
        const result = await axios.get(`${myConstClass.SERVER_LINK}/teams/stats/pagination/${page - 1}/${recordsPage}`);
        setNPages(result.data.totalPages);
        setTeams(result.data.content);
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
            loadTeamsWithPage(currentPage, pages.data.recordsOnPage);
        }
        else {
            setRecordsPerPage(100);
            loadTeamsWithPage(currentPage, 100);
        }
    }
  
    const deleteTeam = async(id)=>{
        try {
            const token = JSON.parse(localStorage.getItem('login')).store;
            await axios.delete(`${myConstClass.SERVER_LINK}/teams/${id}`, {
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
                                <Link className='btn btn-outline-primary mx-1' to={"/addTeam"}>Add Team</Link>
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
                                        <th scope="col">Number of Fans</th>
                                        <th scope="col">Added by</th>
                                        <th scope="col"></th>
                                        <th scope="col">Action</th>
                                        <th scope="col"></th>
                                        <th scope="col"></th>
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
                                                <td>{team.counter}</td>
                                                <td><Link to={`/user/${team.username}`}>{team.username}</Link></td>
                                                <td>
                                                    <Link className='btn btn-primary mx-1' to={`/viewTeam/${team.tid}`}>View</Link>
                                                </td>
                                                <td>
                                                    <Link className='btn btn-outline-primary mx-1' to={`/updateTeam/${team.tid}`} >Edit</Link>
                                                </td>   
                                                <td>
                                                    <button className='btn btn-danger mx-1' onClick={()=>deleteTeam(team.tid)}>Delete</button>
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
    // console.log(currentPage);
    if(currentPage > 1){
        setCurrentPage(currentPage - 1);
        loadTeamsWithPage(currentPage - 1, recordsPerPage);
    }
  }

  function changeCPage(id) { 
    if(id !== '...'){
        setCurrentPage(id);
        loadTeamsWithPage(id, recordsPerPage);
    }
  }

  function nextPage() { 
    // console.log(currentPage);
    if(currentPage < npage) {
        setCurrentPage(currentPage + 1);
        loadTeamsWithPage(currentPage + 1, recordsPerPage);
    }
  }
}
