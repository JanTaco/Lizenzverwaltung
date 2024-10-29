import './Navbar.css'
import logo from './assets/vf-logo.png'

export default function Navbar() {
    return (
    <nav className="nav">
        <img className="navlogo" src={logo} alt="logo"></img>

        <button>Abmelden</button>
   
    </nav>
    )
}