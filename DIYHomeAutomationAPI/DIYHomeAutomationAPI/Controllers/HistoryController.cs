using DIYHomeAutomationAPI.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using System.Reflection;

namespace DIYHomeAutomationAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class HistoryController : ControllerBase
    {
        // variables
        private readonly SensorDbContext _context;

        public HistoryController(SensorDbContext context) => _context = context;

        /// <summary>
        /// This method search in the database all the Histories
        /// </summary>
        /// <returns>List with all the Histories</returns>
        [HttpGet]
        public async Task<ActionResult<IEnumerable<History>>> GetHistory() =>
            // Return an List with all the Histories
            await _context.Histories.ToListAsync();

        /// <summary>
        /// This method search in the database all the Histories that has the same device Id
        /// </summary>
        /// <param name="deviceId">Device id</param>
        /// <returns>List of all the Histories</returns>
        [HttpGet("{deviceId}")]
        public async Task<ActionResult<IEnumerable<History>>> GetHistories(int deviceId) =>
            // Get an List with all the Histories with the same device Id
            await _context.Histories.Where(h => h.DeviceId == deviceId).ToListAsync();

        /// <summary>
        /// This method creates a new History
        /// </summary>
        /// <param name="history">History object</param>
        /// <returns>Method Result</returns>
        [HttpPost]
        public async Task<ActionResult> PostHistory(History? history)
        {
            // Verify if the history receibed is not null
            if (history is null)
               BadRequest();

            // Add the history to an entity entry to insert into the database
            _context.Histories.Add(history);

            // Try to save the entry to the database
            await _context.SaveChangesAsync();

            // If is successfully then returns an OK
            return Ok();
        }

    }
}
