import './MainContent.css';
//test
//test2
import Table from './Table';
import TableLicense from './TableLicense';
import React, { useEffect, useState } from "react";
import CreatePopup from './CreatePopup';

export default function MainContent() {
    const [mode, setMode] = useState('License');
    const [modeButton, setModeButton] = useState('Show Employee');
    const [title, setTitle] = useState('Licenses');
    const [createPopupOn, setCreatePopupOn] = useState(false);
    const [addButtonTitle, setAddButtonTitle] = useState('Add new license');
    const [showExpiredLicenses, setShowExpiredLicenses] = useState(false);
    const[ licenseStatistic, setLicenseStatistic] = useState([]);

    // Beim Laden der Komponente den Zustand aus dem localStorage wiederherstellen
    useEffect(() => {
        const savedShowExpiredLicenses = localStorage.getItem('showExpiredLicenses');
        if (savedShowExpiredLicenses !== null) {
            setShowExpiredLicenses(JSON.parse(savedShowExpiredLicenses));
        }

        // Mode aus dem localStorage wiederherstellen
        const savedMode = localStorage.getItem('mode');
        if (savedMode !== null) {
            setMode(savedMode);
            // Modusabhängige Einstellungen wiederherstellen
            if (savedMode === 'Employee') {
                setModeButton('Show Licenses');
                setTitle('Licensing');
                setAddButtonTitle('Add new licensing');
            }
        }
    }, []);

    useEffect(() => {
        const fetchLincenseInformation = async () => {
            try{
                const response = await fetch(`http://localhost:8080/getLicenseStatistic`, {
                    headers: {
                        'Authorization': 'Basic ' + btoa('Bart:123')
                    }
                });
                const data = await response.json();
                console.log("data lololol: ", data);
                setLicenseStatistic(data);
            }catch (error){
                console.error('Error fetching license information: ', error);
            }
        };
 
        fetchLincenseInformation();
    }, []);

    const changeMode = () => {
        if (mode === 'License') {
            setMode('Employee');
            setModeButton('Show Licenses');
            setTitle('Licensing');
            setAddButtonTitle('Add new licensing');
            // Modus im localStorage speichern
            localStorage.setItem('mode', 'Employee');
        } else {
            setMode('License');
            setModeButton('Show Employee');
            setTitle('Licenses');
            setAddButtonTitle('Add new license');
            // Modus im localStorage speichern
            localStorage.setItem('mode', 'License');
        }
    }

    const closePopup = () => {
        setCreatePopupOn(false);
    };

    const handleClick = () => {
        setCreatePopupOn(true);
    }

    const handleCheckboxChange = (event) => {
        const isChecked = event.target.checked;
        setShowExpiredLicenses(isChecked);
        // Den Zustand in localStorage speichern
        localStorage.setItem('showExpiredLicenses', JSON.stringify(isChecked));
        window.location.reload(true);
    }

    return (
        <>
            <body className='MainContent'>
                <div className="LizenzenStat">
                    Available licenses: 
                    <br />
                    Allocated licenses:
                    <br />
                    Total licenses:
                </div>

                <h1 className='Headline'>{title}</h1>

                <br /><br /><br /><br />

                <button className='SwitchTable' onClick={changeMode}>{modeButton}</button>

                <input type='text' className='Searchbar' placeholder="Einträge durchsuchen" />

                <button type="button" className='Search'>Search</button>

                <label htmlFor="checkExpiredLicenses">Abgelaufene Lizenzen anzeigen</label>
                <input type="checkbox" id='checkExpiredLicenses' checked={showExpiredLicenses} onChange={handleCheckboxChange} />

                <div>
                    <button className='CreateButton' onClick={handleClick}>{addButtonTitle}</button>
                </div>
            </body>
            <Table mode={mode} />

            {createPopupOn && <CreatePopup onClose={closePopup} mode={mode} />}
        </>
    );
}