const EditButton = ({ onClick }) => {
    return ( 
        <td 
            style={{backgroundColor: "red", fontWeight: 450, cursor: "pointer"}} 
            onClick={onClick}
        >
            Bearbeiten
        </td>
    );
}
 
export default EditButton;
