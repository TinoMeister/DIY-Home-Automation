using DIYHomeAutomationAPI.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.IdentityModel.Tokens;
using System.Reflection;

namespace DIYHomeAutomationAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class RestrictionController : ControllerBase
    {
        // variables
        private readonly SensorDbContext _context;

        public RestrictionController(SensorDbContext context) => _context = context;

        [HttpGet]
        public async Task<ActionResult<IEnumerable<Restriction>>> GetRestrictions() =>
            await _context.Restrictions.ToListAsync();

        [HttpPost]
        public async Task<ActionResult<Restriction>> PostRestriction(Restriction? restriction)
        {
            // Verify if the restriction receibed is not null
            if (restriction is null)
                BadRequest();

            // If there is alredy an restriction equals then throw a exception
            if (await _context.Restrictions.Where(r =>
                r.Condition.Equals(restriction.Condition) &&
                r.Name.Equals(restriction.Name) &&
                r.State.Equals(restriction.State) &&
                r.PrimarySensorId.Equals(restriction.PrimarySensorId) &&
                r.PrimarySensorState.Equals(restriction.PrimarySensorState) &&
                r.PrimarySensorValue.Equals(restriction.PrimarySensorValue) &&
                r.SecondarySensorId.Equals(restriction.SecondarySensorId) &&
                r.SecondarySensorValue.Equals(restriction.SecondarySensorValue) && 
                r.SecondarySensorState.Equals(restriction.SecondarySensorState)
                ).FirstOrDefaultAsync() is not null)
                BadRequest();

            // Add the restriction to an entity entry to insert into the database
            _context.Restrictions.Add(restriction);

            // Try to save the entry to the database
            await _context.SaveChangesAsync();

            // If is successfully then returns the last Restriction inserted
            return await _context.Restrictions.OrderByDescending(r => r.Id).FirstAsync();
        }

        [HttpPut("{id}")]
        public async Task<ActionResult> PutRestriction(int id, Restriction? restriction)
        {
            if (restriction is null || id != restriction.Id)
                BadRequest();

            // If there is alredy an restriction equals then throw a exception
            if (await _context.Restrictions.Where(r =>
                !r.Id.Equals(restriction.Id) &&
                r.Condition.Equals(restriction.Condition) &&
                r.Name.Equals(restriction.Name) &&
                r.State.Equals(restriction.State) &&
                r.PrimarySensorId.Equals(restriction.PrimarySensorId) &&
                r.PrimarySensorState.Equals(restriction.PrimarySensorState) &&
                r.PrimarySensorValue.Equals(restriction.PrimarySensorValue) &&
                r.SecondarySensorId.Equals(restriction.SecondarySensorId) &&
                r.SecondarySensorValue.Equals(restriction.SecondarySensorValue) &&
                r.SecondarySensorState.Equals(restriction.SecondarySensorState)
                ).FirstOrDefaultAsync() is not null)
                BadRequest();

            // Put the restriction as an entry an set the sate as modified to update the database
            _context.Entry(restriction).State = EntityState.Modified;

            // Try to save to database
            await _context.SaveChangesAsync();

            // If is successfully then returns an OK
            return Ok();
        }

        [HttpDelete("{id}")]
        public async Task<ActionResult> DeleteRestriction(int id)
        {
            // Verify if the are any restrictions in the database, if not then return NotFound
            if (_context.Restrictions.IsNullOrEmpty())
             BadRequest();
            // Get the restriction by the id
            Restriction? restriction = await _context.Restrictions.FindAsync(id);

            // Send the restriction to the method DeleteRestriction 
            return await DeleteRestriction(restriction);
        }

        /// <summary>
        /// This method removes an Restriction from the database
        /// </summary>
        /// <param name="restriction">Restriction Object</param>
        /// <returns>Action Result</returns>
        [HttpDelete]
        public async Task<ActionResult> DeleteRestriction(Restriction? restriction)
        {
            // Verify if the are any restrictions in the database or if the restriction is null, if not then return NotFound
            if (_context.Restrictions.IsNullOrEmpty() || restriction is null)
                BadRequest();
            // Put the restriction as an entry an set the sate as remove from database
            _context.Restrictions.Remove(restriction);

            // Try to save to database
            await _context.SaveChangesAsync();

            // If is successfully then returns an OK
            return Ok();
        }


    }
}
