import TableLicense from "./TableLicense";
import TableEmployee from "./TableEmployee";
const Table = ({mode}) => {




    if(mode === 'License'){
        return(
            <TableLicense />
        );
    }else{
        return(
            <TableEmployee />
        )
    } 

}
 
export default Table;