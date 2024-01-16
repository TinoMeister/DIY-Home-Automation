using DIYHomeAutomationAPI.Models;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace DIYHomeAutomationAPI.Controllers
{
    [Authorize]
    [Route("api/[controller]")]
    [ApiController]
    public class HistoryController : ControllerBase
    {
        // variables
        private readonly SensorDbContext _context;

        public HistoryController(SensorDbContext context) => _context = context;

        /// <summary>
        /// This method search in the database all the Histories.
        /// </summary>
        /// <param name="userId">User's Id</param>
        /// <returns>List of all the Histories</returns>
        [HttpGet("{userId}")]
        public async Task<ActionResult<IEnumerable<History>>> GetHistories(string userId) =>
            await (
                from r in _context.Rooms
                join d in _context.Devices on r.Id equals d.RoomId
                join h in _context.Histories on d.Id equals h.DeviceId
                where r.UserId == userId
                select h
            ).ToListAsync();

        /// <summary>
        /// This method creates a new History
        /// </summary>
        /// <param name="history">History object</param>
        /// <returns>Method Result</returns>
        [AllowAnonymous]
        [HttpPost]
        public async Task<ActionResult> PostHistory(History? history)
        {
            // Verify if the history receibed is not null
            if (history is null)
               return BadRequest();

            // Add the history to an entity entry to insert into the database
            _context.Histories.Add(history);

            // Try to save the entry to the database
            await _context.SaveChangesAsync();

            // If is successfully then returns an OK
            return Ok();
        }
    }
}
